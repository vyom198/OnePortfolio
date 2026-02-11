import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.vs.oneportfolio.core.gemini_firebase.GeminiData
import com.vs.oneportfolio.core.gemini_firebase.GeminiInputData
import com.vs.oneportfolio.core.gemini_firebase.PortfolioAnalyzer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val geminiModule  = module{
    single { Firebase.ai(backend = GenerativeBackend.googleAI()) }
    single { PortfolioAnalyzer(ai = get(), json = get()) }
     singleOf(::GeminiInputData) bind  GeminiData::class
}
