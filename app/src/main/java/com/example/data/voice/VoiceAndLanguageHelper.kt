package com.example.data.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val nativeLabel: String,
    val speechLocaleTag: String
) {
    EN("EN", "English", "English", "en-KE"),
    SW("SW", "Kiswahili", "Kiswahili", "sw-KE"),
    LG("LG", "Luganda", "Oluganda", "en-UG"),
    LUO("LUO", "Acholi / Luo", "Leb Lwo / Dholuo", "en-KE")
}

data class BilingualPhraseEntry(
    val topic: String,
    val english: String,
    val swahili: String,
    val luganda: String,
    val luo: String
) {
    fun textFor(lang: AppLanguage): String = when (lang) {
        AppLanguage.EN -> english
        AppLanguage.SW -> swahili
        AppLanguage.LG -> luganda
        AppLanguage.LUO -> luo
    }
}

object AgriculturalLanguagePack {

    val uiLabels: Map<String, BilingualPhraseEntry> = mapOf(
        "phi_warning" to BilingualPhraseEntry(
            topic = "Harvest Safety (PHI)",
            english = "Wait until the Pre-Harvest Interval ends before harvesting or selling produce.",
            swahili = "Subiri siku za usalama (PHI) zikamilike kabla ya kuvuna au kuuza mazao.",
            luganda = "Lindirira ennaku z'obukuumi (PHI) ziggweko nga tonnakungula oba kutunda makungula.",
            luo = "Kur nino me gwoko kwo (PHI) otum ma peya ikayo onyo icato cam."
        ),
        "mixing_order" to BilingualPhraseEntry(
            topic = "Knapsack Mixing Order",
            english = "Fill tank halfway with clean water, add WG/WP powders first, then SC liquids, then EC, then foliar/sticker, and top up.",
            swahili = "Jaza maji nusu kwenye bomba, weka unga (WG/WP) kwanza, kisha majimaji (SC), halafu EC, kisha mbolea ya majani na umalizie kujaza maji.",
            luganda = "Jjuza ekitundu ky'amazzi mu ppampu, sooka okutabulamu obuwunga (WG/WP), oddeko amazzi (SC/EC), oluvannyuma ojuzeeko amazzi.",
            luo = "Piki pii i atura dyere, keti yat ma opwoto (WG/WP) mukwongo, dok iket ma pii (SC/EC), lacen ipong atura."
        ),
        "vaccine_cold_chain" to BilingualPhraseEntry(
            topic = "Chick Vaccination Cold Chain",
            english = "Keep live vaccines cold (2-8°C) and mix only with chlorine-free water plus skimmed milk powder.",
            swahili = "Weka chanjo kwenye baridi (2-8°C) na changanya na maji yasiyo na klorini pamoja na unga wa maziwa yasiyo na mafuta.",
            luganda = "Kuuma eddagala ly'obukoko nga linyogoga (2-8°C) era otabule mu mazzi agatalimu kkololiini nga olina n'amata agatalimu muzigo.",
            luo = "Gwok yat gweno ma koyo (2-8°C) ci irub ki pii ma pe tye ki klorin kacel ki cak ma opwoto."
        ),
        "aflatoxin_dry" to BilingualPhraseEntry(
            topic = "Grain Drying & Aflatoxin",
            english = "Dry maize on tarpaulins (never bare soil) to 13% moisture or below before storing in PICS bags.",
            swahili = "Anika mahindi juu ya turubai (kamwe si udongoni) hadi unyevu wa asilimia 13 au chini kabla ya kuhifadhi kwenye mifuko ya PICS.",
            luganda = "Yanika kasooli ku ttundubaali (si ku ttaka) okutuuka ku buleme bwa 13% oba wansi nga tonnatereka mu nsawo za PICS.",
            luo = "Moo anyogi i wi tara (pe i wi ngom) nio ka pii iye oo 13% onyo piny ma peya ikano i kicaa me PICS."
        ),
        "voice_tip" to BilingualPhraseEntry(
            topic = "Ask AI Voice Tip",
            english = "Tap the Microphone to ask about pests, chemical rates, soil pH, poultry, or livestock.",
            swahili = "Bonyeza maikrofoni uulize kuhusu wadudu, vipimo vya dawa, udongo, kuku au mifugo.",
            luganda = "Nyiga akazindaalo obuuze ku biwuka, ebipimo by'eddagala, ettaka, enkoko oba ebisolo.",
            luo = "Dii lak lok wek ipeny pi kudi, pimo yat, ngom, gweno onyo lee."
        )
    )

    val quickVoicePrompts: List<BilingualPhraseEntry> = listOf(
        BilingualPhraseEntry(
            topic = "Fall Armyworm",
            english = "How do I control Fall Armyworm in maize per 20L knapsack?",
            swahili = "Nitatibu vipi viwavijeshi (Fall Armyworm) kwenye mahindi kwa bomba la lita 20?",
            luganda = "Nnyinza ntya okuziyiza obuwuka bwa Fall Armyworm mu kasooli ku ppampu ya liita 20?",
            luo = "Atwero juko kudi me Fall Armyworm i anyogi ki atura lita 20 nining?"
        ),
        BilingualPhraseEntry(
            topic = "Tomato Late Blight",
            english = "My tomato leaves have dark water-soaked blight spots. Which fungicide and PHI?",
            swahili = "Majani ya nyanya yana madoa meusi ya ukungu (Late Blight). Nitumie dawa gani na siku za PHI?",
            luganda = "Ebikoola by'ennyaanya biriko amabala amaddugavu ag'obukuumu (Late Blight). Eddagala ki n'ennaku za PHI?",
            luo = "Pot nyanya tye ki alama macol me two Late Blight. Yat mene ma myero ati kwede ki PHI?"
        ),
        BilingualPhraseEntry(
            topic = "Soil Liming & CEC",
            english = "How much agricultural lime do I apply when soil pH is 5.0?",
            swahili = "Nitaweka chokaa kiasi gani kama pH ya udongo ni 5.0?",
            luganda = "Nsaana okuteekamu chokaa kyenkana wa ng'ettaka lirina pH 5.0?",
            luo = "Abiketo cokaa marom nining ka ngom tye ki pH 5.0?"
        ),
        BilingualPhraseEntry(
            topic = "Poultry Newcastle",
            english = "Explain the Newcastle and Gumboro vaccination schedule for chicks.",
            swahili = "Eleza ratiba ya chanjo ya Newcastle na Gumboro kwa vifaranga.",
            luganda = "Nnyonyola entegeka y'okugema obukoko endwadde ya Newcastle ne Gumboro.",
            luo = "Titira yub me lwoko otino gweno ki yat two Newcastle ki Gumboro."
        )
    )

    fun buildSpeechPromptPrefix(language: AppLanguage): String = when (language) {
        AppLanguage.EN -> ""
        AppLanguage.SW -> "[Respond clearly in Kiswahili mixed with standard Kenyan agricultural terms where helpful] "
        AppLanguage.LG -> "[Respond clearly in Luganda with practical Ugandan/East African farming terms, plus a brief English summary of chemical names/rates] "
        AppLanguage.LUO -> "[Respond clearly in Acholi/Luo with practical Northern Uganda/East African farming terms, plus exact chemical names and ml/L numbers in English] "
    }
}

class FarmTextToSpeechManager(context: Context) {
    private var tts: TextToSpeech? = null
    private var isReady = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _speakingMessageId = MutableStateFlow<String?>(null)
    val speakingMessageId: StateFlow<String?> = _speakingMessageId.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            isReady = status == TextToSpeech.SUCCESS
            if (isReady) {
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                        _speakingMessageId.value = null
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                        _speakingMessageId.value = null
                    }
                })
            }
        }
    }

    fun speak(text: String, language: AppLanguage = AppLanguage.EN, messageId: String? = null) {
        if (!isReady || text.isBlank()) return
        val cleanText = text
            .replace("**", "")
            .replace("*", "")
            .replace("#", "")
            .take(3500)

        val targetLocale = when (language) {
            AppLanguage.SW -> Locale("sw", "KE")
            else -> Locale("en", "KE")
        }
        val res = tts?.setLanguage(targetLocale)
        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
            tts?.setLanguage(Locale.US)
        }
        _speakingMessageId.value = messageId
        _isSpeaking.value = true
        val params = Bundle()
        tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, params, messageId ?: "cropshield_tts")
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _speakingMessageId.value = null
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isSpeaking.value = false
        _speakingMessageId.value = null
    }

    companion object {
        fun buildVoiceRecognitionIntent(language: AppLanguage): Intent {
            return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, language.speechLocaleTag)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your farming question (${language.displayName})...")
            }
        }
    }
}
