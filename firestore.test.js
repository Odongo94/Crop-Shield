const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const { initializeTestEnvironment, assertSucceeds, assertFails } = require('/usr/local/lib/node_modules/@firebase/rules-unit-testing');
const { doc, getDoc, setDoc, updateDoc, deleteDoc, collection, getDocs, query, where, serverTimestamp } = require('/usr/local/lib/node_modules/@firebase/rules-unit-testing/node_modules/firebase/firestore');

const PROJECT_ID = 'demo-no-project';
const DB_ID = 'ai-studio-android-cropshie-bf9fb39e-e7d1-45e0-a9c7-a5304d6dbde1';

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
  await assertFails(getDocs(query(collection(anon, 'extension_reports'), where('farmerId', '==', 'farmer1'))));
});

test('farmer profile allows owner create/read/update and blocks escalation or cross-user access', async () => {
  const f1 = dbFor('farmer1', 'f1@example.com', true);
  const f2 = dbFor('farmer2', 'f2@example.com', true);

  await assertSucceeds(setDoc(doc(f1, 'users/farmer1'), {
    uid: 'farmer1',
    displayName: 'Odongo Vincent',
    farmName: 'Njoro Shamba',
    county: 'Nakuru',
    preferredLanguage: 'SW',
    role: 'farmer',
    createdAt: serverTimestamp()
  }));

  await assertSucceeds(getDoc(doc(f1, 'users/farmer1')));
  await assertFails(getDoc(doc(f2, 'users/farmer1')));
  await assertFails(getDocs(collection(f1, 'users')));

  // Cannot self-escalate role
  await assertFails(updateDoc(doc(f1, 'users/farmer1'), { role: 'advisor' }));

  // Can update displayName and preferredLanguage
  await assertSucceeds(updateDoc(doc(f1, 'users/farmer1'), { preferredLanguage: 'LG' }));
});

test('user farm_records allow owner CRUD and block cross-user reads/writes and immutable createdAt tampering', async () => {
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
  await assertFails(setDoc(doc(f2, 'users/farmer1/farm_records/rec_hack'), {
    ownerId: 'farmer1',
    recordType: 'FIELD_NOTE',
    title: 'Hack',
    cropOrSpecies: 'Maize',
    summary: 'Bad',
    payloadJson: '{}',
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp()
  }));

  await assertSucceeds(updateDoc(doc(f1, 'users/farmer1/farm_records/rec_1'), {
    summary: 'Updated PHI notes',
    updatedAt: serverTimestamp()
  }));
  await assertSucceeds(deleteDoc(doc(f1, 'users/farmer1/farm_records/rec_1')));
});

test('extension_reports support owner queries and verified advisor review without exposing to strangers', async () => {
  const farmer = dbFor('farmer1', 'farmer@example.com', true);
  const verifiedAdvisor = dbFor('adv1', 'advisor@kalro.org', true);
  const unverifiedAdvisor = dbFor('adv2', 'advisor@kalro.org', false);
  const stranger = dbFor('stranger', 'stranger@example.com', true);

  await assertSucceeds(setDoc(doc(farmer, 'extension_reports/rep_1'), {
    farmerId: 'farmer1',
    farmerName: 'Wanjiku Kamau',
    county: 'Kiambu',
    title: 'Maize Fall Armyworm Scouting Report',
    summary: '25% whorl damage in Plot B; requesting biocontrol review.',
    advisorEmail: 'advisor@kalro.org',
    advisorNotes: '',
    status: 'OPEN',
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp()
  }));

  // Owner & verified advisor can get and list their reports
  await assertSucceeds(getDocs(query(collection(farmer, 'extension_reports'), where('farmerId', '==', 'farmer1'))));
  await assertSucceeds(getDocs(query(collection(verifiedAdvisor, 'extension_reports'), where('advisorEmail', '==', 'advisor@kalro.org'))));

  // Unverified advisor & stranger denied
  await assertFails(getDoc(doc(unverifiedAdvisor, 'extension_reports/rep_1')));
  await assertFails(getDoc(doc(stranger, 'extension_reports/rep_1')));

  // Verified advisor can add notes and mark REVIEWED, but cannot change farmerId or title
  await assertSucceeds(updateDoc(doc(verifiedAdvisor, 'extension_reports/rep_1'), {
    advisorNotes: 'Release Trichogramma + neem EC at dusk.',
    status: 'REVIEWED',
    updatedAt: serverTimestamp()
  }));
  await assertFails(updateDoc(doc(verifiedAdvisor, 'extension_reports/rep_1'), {
    title: 'Tampered title',
    updatedAt: serverTimestamp()
  }));
});
