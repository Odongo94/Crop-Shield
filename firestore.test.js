const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const { initializeTestEnvironment, assertSucceeds, assertFails } = require('/usr/local/lib/node_modules/@firebase/rules-unit-testing');
const { doc, getDoc, setDoc, updateDoc, deleteDoc, collection, getDocs, query, where, serverTimestamp } = require('/usr/local/lib/node_modules/@firebase/rules-unit-testing/node_modules/firebase/firestore');

const PROJECT_ID = 'demo-no-project';
const DB_ID = 'ai-studio-android-cropshie-bf9fb39e-e7d1-45e0-a9c7-a5304d6dbde1';
const ADMIN_EMAIL = 'odongovincento@gmail.com';

let testEnv;

test.before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules: fs.readFileSync('firestore.rules', 'utf8'),
      host: '127.0.0.1',
      port: 8085,
    },
  });
});

test.after(async () => {
  if (testEnv) await testEnv.cleanup();
});

test.beforeEach(async () => {
  await testEnv.clearFirestore();
});

function dbFor(uid, email, emailVerified = false) {
  if (!uid) {
    return testEnv.unauthenticatedContext().firestore(DB_ID);
  }
  const token = email ? { email, email_verified: emailVerified } : {};
  return testEnv.authenticatedContext(uid, token).firestore(DB_ID);
}

test('unauthenticated users are denied everywhere', async () => {
  const anon = dbFor(null);
  await assertFails(setDoc(doc(anon, 'users/farmer1'), {
    uid: 'farmer1', displayName: 'John', farmName: 'Shamba', county: 'Nakuru',
    preferredLanguage: 'EN', role: 'farmer', createdAt: serverTimestamp()
  }));
  await assertFails(getDoc(doc(anon, 'users/farmer1')));
  await assertFails(getDocs(collection(anon, 'farmer_registry')));
  await assertFails(getDocs(collection(anon, 'admin_notifications')));
  await assertFails(getDocs(query(collection(anon, 'community_messages'), where('roomId', '==', 'global_shamba_community'))));
  await assertFails(getDocs(query(collection(anon, 'extension_reports'), where('farmerId', '==', 'farmer1'))));
});

test('farmer profile allows owner create/read/update and blocks escalation or cross-user access', async () => {
  const f1 = dbFor('farmer1', 'f1@example.com', true);
  const f2 = dbFor('farmer2', 'f2@example.com', true);

  await assertSucceeds(setDoc(doc(f1, 'users/farmer1'), {
    uid: 'farmer1',
    displayName: 'Grace Achieng',
    farmName: 'Njoro Shamba',
    county: 'Nakuru',
    preferredLanguage: 'SW',
    role: 'farmer',
    createdAt: serverTimestamp()
  }));

  await assertSucceeds(getDoc(doc(f1, 'users/farmer1')));
  await assertFails(getDoc(doc(f2, 'users/farmer1')));
  await assertFails(getDocs(collection(f1, 'users')));
  await assertFails(updateDoc(doc(f1, 'users/farmer1'), { role: 'advisor' }));
  await assertSucceeds(updateDoc(doc(f1, 'users/farmer1'), { preferredLanguage: 'LG' }));
});

test('farmer_registry PII (emails & coordinates) is strictly hidden from other farmers and visible only to verified super admin', async () => {
  const f1 = dbFor('farmer1', 'wanjiku@farm.ke', true);
  const f2 = dbFor('farmer2', 'otieno@farm.ke', true);
  const spoofedAdmin = dbFor('spoof1', ADMIN_EMAIL, false);
  const superAdmin = dbFor('admin1', ADMIN_EMAIL, true);

  // Farmer1 records their own registry entry
  await assertSucceeds(setDoc(doc(f1, 'farmer_registry/farmer1'), {
    uid: 'farmer1',
    email: 'wanjiku@farm.ke',
    displayName: 'Wanjiku Kamau',
    farmName: 'Limuru Greens',
    county: 'Kiambu (Thika / Limuru)',
    locality: 'Limuru Town',
    latitude: -1.1069,
    longitude: 36.6425,
    primaryCrops: 'Roses, French Beans',
    signInCount: 1,
    lastActiveAt: serverTimestamp(),
    createdAt: serverTimestamp()
  }));

  // Farmer1 cannot spoof someone else's email
  await assertFails(setDoc(doc(f2, 'farmer_registry/farmer2'), {
    uid: 'farmer2',
    email: 'fake@other.com',
    displayName: 'Otieno',
    farmName: 'Lake Farm',
    county: 'Kisumu',
    locality: 'Ahero',
    latitude: -0.17,
    longitude: 34.91,
    primaryCrops: 'Rice',
    signInCount: 1,
    lastActiveAt: serverTimestamp(),
    createdAt: serverTimestamp()
  }));

  // Farmer1 can read their own entry, but CANNOT read Farmer2 or list all farmers
  await assertSucceeds(getDoc(doc(f1, 'farmer_registry/farmer1')));
  await assertFails(getDoc(doc(f2, 'farmer_registry/farmer1')));
  await assertFails(getDocs(collection(f1, 'farmer_registry')));
  await assertFails(getDocs(collection(f2, 'farmer_registry')));

  // Spoofed unverified admin email is rejected
  await assertFails(getDocs(collection(spoofedAdmin, 'farmer_registry')));
  await assertFails(getDoc(doc(spoofedAdmin, 'farmer_registry/farmer1')));

  // Verified super admin CAN list and read all farmer emails & localities
  const snap = await assertSucceeds(getDocs(collection(superAdmin, 'farmer_registry')));
  assert.equal(snap.size, 1);
  assert.equal(snap.docs[0].data().email, 'wanjiku@farm.ke');
});

test('admin_notifications can be created by farmer for themselves and read/marked read ONLY by verified super admin', async () => {
  const f1 = dbFor('farmer1', 'wanjiku@farm.ke', true);
  const f2 = dbFor('farmer2', 'otieno@farm.ke', true);
  const spoofedAdmin = dbFor('spoof1', ADMIN_EMAIL, false);
  const superAdmin = dbFor('admin1', ADMIN_EMAIL, true);

  await assertSucceeds(setDoc(doc(f1, 'admin_notifications/notif_1'), {
    farmerId: 'farmer1',
    farmerName: 'Wanjiku Kamau',
    farmerEmail: 'wanjiku@farm.ke',
    county: 'Kiambu',
    locality: 'Limuru',
    eventType: 'NEW_FARMER',
    summary: 'New farmer registered from Limuru',
    isRead: false,
    createdAt: serverTimestamp()
  }));

  // Cannot create notification impersonating another user or pre-marked read
  await assertFails(setDoc(doc(f2, 'admin_notifications/notif_bad'), {
    farmerId: 'farmer1',
    farmerName: 'Spoof',
    farmerEmail: 'wanjiku@farm.ke',
    county: 'Kiambu',
    locality: 'Limuru',
    eventType: 'NEW_FARMER',
    summary: 'Spoof',
    isRead: false,
    createdAt: serverTimestamp()
  }));

  // Farmers & unverified admins cannot read notifications
  await assertFails(getDocs(collection(f1, 'admin_notifications')));
  await assertFails(getDocs(collection(spoofedAdmin, 'admin_notifications')));

  // Verified super admin reads and marks read
  await assertSucceeds(getDocs(collection(superAdmin, 'admin_notifications')));
  await assertSucceeds(updateDoc(doc(superAdmin, 'admin_notifications/notif_1'), { isRead: true }));
});

test('community_messages allows farmers & super_admin to collaborate while preventing role spoofing', async () => {
  const f1 = dbFor('farmer1', 'wanjiku@farm.ke', true);
  const superAdmin = dbFor('admin1', ADMIN_EMAIL, true);

  // Farmer posts question
  await assertSucceeds(setDoc(doc(f1, 'community_messages/msg_1'), {
    roomId: 'global_shamba_community',
    authorId: 'farmer1',
    authorName: 'Wanjiku Kamau',
    authorRole: 'farmer',
    county: 'Kiambu',
    topic: 'Pests & IPM',
    messageText: 'Spotted whiteflies under my tomato leaves, what IPM option works?',
    isSolutionPinned: false,
    createdAt: serverTimestamp()
  }));

  // Farmer CANNOT impersonate super_admin or pin their own message
  await assertFails(setDoc(doc(f1, 'community_messages/msg_spoof'), {
    roomId: 'global_shamba_community',
    authorId: 'farmer1',
    authorName: 'Wanjiku Kamau',
    authorRole: 'super_admin',
    county: 'Kiambu',
    topic: 'Pests & IPM',
    messageText: 'Fake admin advice',
    isSolutionPinned: false,
    createdAt: serverTimestamp()
  }));

  // Super admin responds and pins solution
  await assertSucceeds(setDoc(doc(superAdmin, 'community_messages/msg_2'), {
    roomId: 'global_shamba_community',
    authorId: 'admin1',
    authorName: 'Agronomy Advisor',
    authorRole: 'super_admin',
    county: 'National Advisory',
    topic: 'Pests & IPM',
    messageText: 'Use yellow sticky traps + neem oil (Azadirachtin) or Spiromesifen at dusk.',
    isSolutionPinned: true,
    createdAt: serverTimestamp()
  }));

  // Client-triggered Shamba AI Agronomist auto-response / fact-check is allowed (unpinned)
  await assertSucceeds(setDoc(doc(f1, 'community_messages/ai_resp_msg_1'), {
    roomId: 'global_shamba_community',
    authorId: 'farmer1',
    authorName: 'Shamba AI Agronomist',
    authorRole: 'ai_advisor',
    county: 'AI Agronomist',
    topic: 'Pests & IPM',
    messageText: 'Grounded IPM check: Use yellow sticky traps and neem oil (Azadirachtin).',
    isSolutionPinned: false,
    createdAt: serverTimestamp()
  }));

  // AI advisor cannot self-pin or spoof authorName
  await assertFails(setDoc(doc(f1, 'community_messages/ai_bad_pin'), {
    roomId: 'global_shamba_community',
    authorId: 'farmer1',
    authorName: 'Shamba AI Agronomist',
    authorRole: 'ai_advisor',
    county: 'AI Agronomist',
    topic: 'Pests & IPM',
    messageText: 'Self pinned attempt',
    isSolutionPinned: true,
    createdAt: serverTimestamp()
  }));

  // Both farmers and admin can query the community room
  const res = await assertSucceeds(
    getDocs(query(collection(f1, 'community_messages'), where('roomId', '==', 'global_shamba_community')))
  );
  assert.equal(res.size, 3);

  // Unfiltered query without roomId is rejected (rules are not filters)
  await assertFails(getDocs(collection(f1, 'community_messages')));
});

test('direct_threads isolates 1-on-1 chat to thread owner and verified super admin', async () => {
  const f1 = dbFor('farmer1', 'wanjiku@farm.ke', true);
  const f2 = dbFor('farmer2', 'otieno@farm.ke', true);
  const superAdmin = dbFor('admin1', ADMIN_EMAIL, true);

  await assertSucceeds(setDoc(doc(f1, 'direct_threads/farmer1'), {
    farmerId: 'farmer1',
    farmerName: 'Wanjiku Kamau',
    county: 'Kiambu',
    locality: 'Limuru',
    lastMessagePreview: 'Hello admin, my maize leaves are yellowing.',
    lastSenderRole: 'farmer',
    unreadByAdmin: true,
    unreadByFarmer: false,
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp()
  }));

  await assertSucceeds(setDoc(doc(f1, 'direct_threads/farmer1/messages/dmsg_1'), {
    farmerId: 'farmer1',
    senderId: 'farmer1',
    senderName: 'Wanjiku Kamau',
    senderRole: 'farmer',
    text: 'Hello admin, my maize leaves are yellowing.',
    createdAt: serverTimestamp()
  }));

  // Farmer2 cannot read or write Farmer1's direct thread
  await assertFails(getDoc(doc(f2, 'direct_threads/farmer1')));
  await assertFails(getDocs(collection(f2, 'direct_threads/farmer1/messages')));
  await assertFails(setDoc(doc(f2, 'direct_threads/farmer1/messages/dmsg_hack'), {
    farmerId: 'farmer1',
    senderId: 'farmer2',
    senderName: 'Otieno',
    senderRole: 'farmer',
    text: 'Intruder',
    createdAt: serverTimestamp()
  }));

  // Super admin can list all direct threads, read messages, and reply
  await assertSucceeds(getDocs(collection(superAdmin, 'direct_threads')));
  await assertSucceeds(getDocs(collection(superAdmin, 'direct_threads/farmer1/messages')));
  await assertSucceeds(setDoc(doc(superAdmin, 'direct_threads/farmer1/messages/dmsg_2'), {
    farmerId: 'farmer1',
    senderId: 'admin1',
    senderName: 'Odongo Vincent',
    senderRole: 'super_admin',
    text: 'Check topdress timing with CAN 26%N and inspect for stemborer.',
    createdAt: serverTimestamp()
  }));
});

test('app_feedback allows farmer submit & read own while super admin reads all', async () => {
  const f1 = dbFor('farmer1', 'wanjiku@farm.ke', true);
  const f2 = dbFor('farmer2', 'otieno@farm.ke', true);
  const superAdmin = dbFor('admin1', ADMIN_EMAIL, true);

  await assertSucceeds(setDoc(doc(f1, 'app_feedback/fb_1'), {
    userId: 'farmer1',
    farmerName: 'Wanjiku Kamau',
    county: 'Kiambu',
    featureSource: 'AI_AGRONOMIST',
    questionOrTopic: 'Fall Armyworm control',
    wasHelpful: true,
    improvementArea: 'Local Languages (SW/LG/LUO)',
    improvementSuggestion: 'Add more Swahili voice examples for knapsack mixing.',
    createdAt: serverTimestamp()
  }));

  await assertSucceeds(getDocs(query(collection(f1, 'app_feedback'), where('userId', '==', 'farmer1'))));
  await assertFails(getDoc(doc(f2, 'app_feedback/fb_1')));
  await assertFails(getDocs(collection(f2, 'app_feedback')));

  const allFb = await assertSucceeds(getDocs(collection(superAdmin, 'app_feedback')));
  assert.equal(allFb.size, 1);
});

test('user farm_records allow owner CRUD and block cross-user reads/writes', async () => {
  const f1 = dbFor('farmer1', 'f1@example.com', true);
  const f2 = dbFor('farmer2', 'f2@example.com', true);

  await assertSucceeds(setDoc(doc(f1, 'users/farmer1/farm_records/rec_1'), {
    ownerId: 'farmer1',
    recordType: 'PHI_SPRAY',
    title: 'Tomato Late Blight Spray',
    cropOrSpecies: 'Tomato',
    summary: 'Ridomil Gold 50g/20L, PHI 7 days',
    payloadJson: '{"phiDays":7}',
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp()
  }));

  await assertSucceeds(getDocs(collection(f1, 'users/farmer1/farm_records')));
  await assertFails(getDocs(collection(f2, 'users/farmer1/farm_records')));
  await assertSucceeds(updateDoc(doc(f1, 'users/farmer1/farm_records/rec_1'), {
    summary: 'Updated PHI notes',
    updatedAt: serverTimestamp()
  }));
  await assertSucceeds(deleteDoc(doc(f1, 'users/farmer1/farm_records/rec_1')));
});

test('extension_reports support owner queries and verified advisor review', async () => {
  const farmer = dbFor('farmer1', 'farmer@example.com', true);
  const verifiedAdvisor = dbFor('adv1', 'advisor@kalro.org', true);
  const stranger = dbFor('stranger', 'stranger@example.com', true);

  await assertSucceeds(setDoc(doc(farmer, 'extension_reports/rep_1'), {
    farmerId: 'farmer1',
    farmerName: 'Wanjiku Kamau',
    county: 'Kiambu',
    title: 'Maize Fall Armyworm Scouting Report',
    summary: '25% whorl damage in Plot B.',
    advisorEmail: 'advisor@kalro.org',
    advisorNotes: '',
    status: 'OPEN',
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp()
  }));

  await assertSucceeds(getDocs(query(collection(farmer, 'extension_reports'), where('farmerId', '==', 'farmer1'))));
  await assertSucceeds(getDocs(query(collection(verifiedAdvisor, 'extension_reports'), where('advisorEmail', '==', 'advisor@kalro.org'))));
  await assertFails(getDoc(doc(stranger, 'extension_reports/rep_1')));
});
