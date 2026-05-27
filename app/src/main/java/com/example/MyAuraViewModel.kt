package com.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
    Onboarding,
    Survey,
    Main,
    CheckIn,
    CheckOut,
    ActiveRoutine,
    Chat,
    WorkshopDetail,
    MasterclassesList,
    EventsList,
    SpecialActivities,
    Calendar,
    Corporate,
    Progress,
    Tienda
}

enum class MainTab {
    Home,
    Explore,
    Profile
}

data class ChatMessage(
    val sender: String, // "Aura" or "User"
    val text: String,
    val time: String,
    val isSystemSuggestion: Boolean = false,
    val suggestedRoutine: Routine? = null
)

data class CartItem(
    val item: ShopItem,
    var quantity: Int
)

data class Invitation(
    val id: String,
    val title: String,
    val date: String,
    val place: String,
    val objective: String,
    val isConfirmed: Boolean = false
)

data class AuraChatDecision(
    val dateString: String,
    val textSummary: String,
    val decision: String // "Aceptó rutina: [name]" or "Decidió: Ya me siento mejor"
)

data class MyAuraState(
    val currentScreen: Screen = Screen.Onboarding,
    val currentMainTab: MainTab = MainTab.Home,
    
    // User Profile
    val userName: String = "",
    val ageRange: String = "",
    val gender: String = "",
    val routineDescription: String = "",
    val wakeUpTime: String = "07:30 AM",
    val endOfDayTime: String = "09:00 PM",
    val sleepTime: String = "10:30 PM",
    val workoutHabit: String = "",
    val dayFeeling: String = "",
    val stressScale: Int = 5,
    val goals: List<String> = emptyList(),
    val routineTimeMinutes: Int = 5,
    val activeKit: String = "Aún no tengo kit",
    val activeSubscription: String = "Plan Básico",
    val linkSmartwatch: String = "No por ahora",
    val auraMoments: List<String> = emptyList(),
    
    // Invitations and Aura IA Decisions
    val invitations: List<Invitation> = listOf(
        Invitation(
            "inv1",
            "Taller de Regulación & Aromaterapia 🌸",
            "La próxima semana (28 de Mayo), 6:30 PM",
            "Sede MyAura El Poblado, Medellín",
            "Aprender a sincronizar micro-pausas y blends con tus horarios de estudio o trabajo."
        ),
        Invitation(
            "inv2",
            "Encuentro Meditación & Té Orgánico 🍵",
            "Martes entrante (02 de Junio), 4:00 PM",
            "Invernadero Laureles, Medellín",
            "Espacio íntimo para vaciar la mente, compartir retos emocionales y degustar una infusión premium."
        )
    ),
    val auraChatHistoryDecisions: List<AuraChatDecision> = emptyList(),
    
    // Custom user plan
    val rawAgendaText: String = "",
    val notesForTomorrow: String = "",
    val finishedPendingsOption: String = "",
    
    // Check-in State - 8 questions
    val checkInCompletedToday: Boolean = false,
    val checkInWakeFeeling: String = "",       // Q1: ¿Cómo amaneciste?
    val checkInSleepDuration: String = "",     // Q2: ¿Cuántas horas dormiste?
    val checkInSleepQuality: String = "",      // Q3: ¿Cómo fue la calidad del sueño?
    val checkInEnergy: String = "Media",       // Q4: ¿Cómo está tu energía?
    val checkInStress: Int = 5,                // Q5: Nivel de estrés (1-10)
    val checkInDayLoad: String = "",           // Q6: ¿Qué tan cargado se ve tu día?
    val checkInNeedsToday: String = "Calma",   // Q7: ¿Qué necesitas más hoy?
    // Q8: Escribir lista de tareas -> saved in rawAgendaText
    
    val checkInDreams: String = "",
    val checkInWakeRested: String = "",
    val checkInRemindPause: Boolean = false,
    val todayTodoTasks: List<String> = listOf(
        "8:00 AM - Meditación y Despertar Sensorial con Kit PAUSA 🌸",
        "11:00 AM - Bloque de Enfoque Mental y Respiración 🎯",
        "11:30 AM - Pausa Corta de Aromaterapia con Roll-on MyAura 🍀",
        "2:00 PM - Caminata Consciente MyAura 👣",
        "9:00 PM - Desconexión de Pantallas y Ritual con Vela 🌙"
    ),
    
    // Check-out State - 8 questions
    val checkOutCompletedToday: Boolean = false,
    val checkOutFinishedDay: String = "",      // Q1: ¿Ya finalizaste tu día?
    val checkOutDayFeel: String = "",          // Q2: ¿Cómo te sientes al cerrar el día?
    val checkOutHeaviestThing: String = "",    // Q3: ¿Qué fue lo más pesado de hoy?
    val checkOutMentalLoad: Int = 5,           // Q4: Nivel de carga mental (1-10)
    val checkOutLetGo: String = "",            // Q5: ¿Sientes que necesitas soltar algo?
    val checkOutBodyStatus: String = "",       // Q6: ¿Cómo está tu cuerpo?
    val checkOutNeedsTonight: String = "",     // Q7: ¿Qué necesitas para cerrar mejor?
    val checkOutFinishedPendings: String = "", // Q8: ¿Lograste terminar todos los pendientes?
    
    // Chat History
    val chatMessages: List<ChatMessage> = listOf(
        ChatMessage("Aura", "Hola ✨ ¿Cómo ha ido tu día? Estoy aquí contigo. Puedes contarme lo que quieras, sin presión.", "18:30")
    ),
    val isAuraTyping: Boolean = false,

    // Data lists updated dynamically with pre-populated values so views are informative and interactive from start
    val routinesList: List<Routine> = StaticData.independentRoutines,
    val selectedRoutine: Routine? = null,
    val workshopsList: List<Workshop> = StaticData.workshops.map { if (it.id == "w1") it.copy(isPurchased = true) else it },
    val activeWorkshop: Workshop? = null,
    val masterclassesList: List<Masterclass> = StaticData.masterclasses.map { if (it.id == "m1" || it.id == "m3") it.copy(isPurchased = true) else it },
    val eventsList: List<PhysicalEvent> = StaticData.physicalEvents.map { if (it.id == "pe1" || it.id == "pe2" || it.id == "pe3") it.copy(isReserved = true) else it },
    val activationsList: List<Activation> = StaticData.activations,
    val achievementsList: List<Achievement> = StaticData.achievements,
    val shoppingCart: List<CartItem> = emptyList(),
    
    // Smartwatch Data Simulation
    val smartwatchData: SmartwatchData = SmartwatchData(
        steps = 5420,
        currentStressLevel = 7,
        hrCurrent = 76,
        hrAverage = 72,
        activeMinutes = 24,
        sleepString = "6 h 40 min",
        oxygenLevel = 98,
        hrvValue = 54,
        recoveryScore = 82
    ),
    
    // Corporate Mode
    val corporateRole: String? = null, // null = not chosen, "host" = Soy anfitrión, "guest" = Soy usuario invitado
    val corporateCommunityType: String = "Empresa", // "Empresa", "Universidad", "Gimnasio", "Estudio de pilates/yoga", "Centro deportivo", "Comunidad privada", "Otro"
    val corporateCommunityName: String = "Aura Team Medellín",
    val corporateActiveMembers: Int = 34,
    val corporateTotalMembers: Int = 50,
    val corporateGroupStressLevel: String = "Alto (7/10)",
    val corporateGroupEnergyLevel: String = "Media-Baja",
    val corporateGroupTopNeed: String = "Calma y enfoque",
    val corporateBreakAssigned: Boolean = false,
    val corporateAssignedRoutines: List<String> = listOf("r1", "r2"), // list of routine IDs currently active
    val corporateActiveChallenge: String = "Reto de respiración de 3 minutos",
    val corporateChallengeParticipantsCount: Int = 24,
    val corporateChallengeCompletedCount: Int = 18,
    val corporateIsJoinedChallenge: Boolean = false,
    val corporateAssignedWorkshops: List<String> = listOf("w1", "w2", "w3"),
    val corporateWeeklyCheckins: Int = 124,
    val corporateWeeklyRoutines: Int = 89,
    val corporateWeeklyChallenges: Int = 3
)

class MyAuraViewModel : ViewModel() {
    private val _state = MutableStateFlow(MyAuraState())
    val state: StateFlow<MyAuraState> = _state.asStateFlow()

    fun updateScreen(screen: Screen) {
        _state.value = _state.value.copy(currentScreen = screen)
    }

    fun updateMainTab(tab: MainTab) {
        _state.value = _state.value.copy(currentMainTab = tab)
    }

    fun updateUserName(name: String) {
        _state.value = _state.value.copy(userName = name)
    }

    fun setSurveyAnswer(questionIndex: Int, answer: Any) {
        val currentState = _state.value
        when (questionIndex) {
            1 -> _state.value = currentState.copy(ageRange = answer.toString())
            2 -> _state.value = currentState.copy(gender = answer.toString())
            3 -> _state.value = currentState.copy(routineDescription = answer.toString())
            4 -> _state.value = currentState.copy(wakeUpTime = answer.toString())
            5 -> _state.value = currentState.copy(endOfDayTime = answer.toString())
            6 -> _state.value = currentState.copy(sleepTime = answer.toString())
            7 -> _state.value = currentState.copy(workoutHabit = answer.toString())
            8 -> _state.value = currentState.copy(dayFeeling = answer.toString())
            9 -> _state.value = currentState.copy(stressScale = answer.toString().toIntOrNull() ?: 5)
            10 -> {
                val list = answer as? List<String> ?: emptyList()
                _state.value = currentState.copy(goals = list)
            }
            11 -> _state.value = currentState.copy(routineTimeMinutes = answer.toString().toIntOrNull() ?: 5)
            12 -> {
                val kit = answer.toString()
                val sub = if (kit == "Sí, Kit RITUAL") "Plan Premium ⭐" else currentState.activeSubscription
                _state.value = currentState.copy(activeKit = kit, activeSubscription = sub)
            }
            13 -> _state.value = currentState.copy(linkSmartwatch = answer.toString())
            14 -> {
                val list = answer as? List<String> ?: emptyList()
                _state.value = currentState.copy(auraMoments = list)
            }
        }
    }

    fun completeCheckIn(
        energy: String = "Media",
        stress: Int = 5,
        wakeFeeling: String = "",
        sleepQuality: String = "",
        sleepDuration: String = "",
        dreams: String = "",
        wakeRested: String = "",
        needsToday: String = "Calma",
        remindPause: Boolean = false,
        todosList: List<String> = emptyList(),
        agendaText: String = "",
        dayLoad: String = "" // Q6: ¿Qué tan cargado se ve tu día?
    ) {
        _state.value = _state.value.copy(
            checkInCompletedToday = true,
            checkOutCompletedToday = false,
            checkInEnergy = energy,
            checkInStress = stress,
            checkInWakeFeeling = wakeFeeling,
            checkInSleepQuality = sleepQuality,
            checkInSleepDuration = sleepDuration,
            checkInDreams = dreams,
            checkInWakeRested = wakeRested,
            checkInNeedsToday = needsToday,
            checkInDayLoad = dayLoad,
            checkInRemindPause = remindPause,
            todayTodoTasks = if (todosList.isEmpty()) _state.value.todayTodoTasks else todosList,
            rawAgendaText = agendaText,
            currentScreen = Screen.Main
        )
        // Unlock 7 days racha check-in achievement if not done index
        unlockAchievement("ach1")
    }

    fun completeCheckOut(
        dayFeel: String = "",          // Q2
        needsTonight: String = "",     // Q7
        finishedPendingsOption: String = "", // Q8
        notesForTomorrow: String = "",
        finishedDay: String = "",      // Q1
        heaviestThing: String = "",    // Q3
        mentalLoad: Int = 5,           // Q4
        letGo: String = "",            // Q5
        bodyStatus: String = "",       // Q6
        finishedPendings: String = ""  // Q8 alternate
    ) {
        _state.value = _state.value.copy(
            checkOutCompletedToday = true,
            checkInCompletedToday = false, // mark as false so next day starts with check-in pending
            checkOutFinishedDay = finishedDay,
            checkOutDayFeel = dayFeel,
            checkOutHeaviestThing = heaviestThing,
            checkOutMentalLoad = mentalLoad,
            checkOutLetGo = letGo,
            checkOutBodyStatus = bodyStatus,
            checkOutNeedsTonight = needsTonight,
            checkOutFinishedPendings = if (finishedPendings.isNotEmpty()) finishedPendings else finishedPendingsOption,
            finishedPendingsOption = if (finishedPendings.isNotEmpty()) finishedPendings else finishedPendingsOption,
            notesForTomorrow = notesForTomorrow,
            currentScreen = Screen.Main
        )
        unlockAchievement("ach3")
    }

    fun selectCorporateRole(role: String?) {
        _state.value = _state.value.copy(corporateRole = role)
    }

    fun updateCorporateCommunity(type: String, name: String) {
        _state.value = _state.value.copy(
            corporateCommunityType = type,
            corporateCommunityName = name
        )
    }

    fun assignCorporateRoutine(routineId: String) {
        val list = _state.value.corporateAssignedRoutines.toMutableList()
        if (list.contains(routineId)) {
            list.remove(routineId)
        } else {
            list.add(routineId)
        }
        _state.value = _state.value.copy(corporateAssignedRoutines = list)
    }

    fun toggleCorporateChallengeJoined() {
        val currentJoined = _state.value.corporateIsJoinedChallenge
        val nextJoined = !currentJoined
        val countDelta = if (nextJoined) 1 else -1
        _state.value = _state.value.copy(
            corporateIsJoinedChallenge = nextJoined,
            corporateChallengeCompletedCount = _state.value.corporateChallengeCompletedCount + countDelta
        )
    }

    fun changeCorporateActiveChallenge(title: String) {
        _state.value = _state.value.copy(
            corporateActiveChallenge = title,
            corporateChallengeCompletedCount = 12,
            corporateIsJoinedChallenge = false
        )
    }

    fun selectRoutine(routine: Routine) {
        _state.value = _state.value.copy(selectedRoutine = routine, currentScreen = Screen.ActiveRoutine)
    }

    fun tickRoutineStep() {
        val currRoutine = _state.value.selectedRoutine ?: return
        if (currRoutine.currentStepIndex < currRoutine.steps.size - 1) {
            currRoutine.currentStepIndex++
            _state.value = _state.value.copy(selectedRoutine = currRoutine.copy())
        } else {
            // Completed!
            currRoutine.isCompleted = true
            currRoutine.currentStepIndex = 0
            val updatedList = _state.value.routinesList.map {
                if (it.id == currRoutine.id) currRoutine else it
            }
            _state.value = _state.value.copy(
                routinesList = updatedList,
                selectedRoutine = null,
                currentScreen = Screen.Main
            )
            unlockAchievement("ach6") // cumulative calmness minutes
        }
    }

    fun sendMessageToAura(text: String) {
        if (text.trim().isEmpty()) return
        val timeNow = "18:33"
        val updatedMessages = _state.value.chatMessages + ChatMessage("User", text, timeNow)
        _state.value = _state.value.copy(chatMessages = updatedMessages, isAuraTyping = true)

        viewModelScope.launch {
            try {
                val name = if (_state.value.userName.isEmpty()) "Juli" else _state.value.userName
                val systemPrompt = """
                    Eres Aura, el alma y guía de bienestar de MyAura. Tu tono es extremadamente suave, calmado, amigable, cercano y natural. Háblale al usuario como una amiga o mentora de absoluta confianza espiritual. Evita sonar robótica, corporativa o clínica.

                    Lineamientos críticos de conversación:
                    1. Valida de inmediato y con profunda calidez lo que comparte el usuario. Usa expresiones empáticas como: "Te entiendo.", "Gracias por contármelo.", "No tienes que resolver todo ahora.", "Podemos ir suave.", "Vamos paso a paso.", "Si quieres, primero te escucho y luego vemos qué te puede ayudar."
                    2. No presiones con rutinas ni recomiendes soluciones apuradas al principio. Deja que el usuario hable y se desahogue.
                    3. Haz preguntas de seguimiento suaves y abiertas para ayudarlo a ventilar y comprender su estado. Por ejemplo: "¿Quieres contarme qué pasó o desde cuándo te sientes así?", "¿Sientes esa ansiedad en la mente o en el cuerpo?", "¿Cómo sientes tu respiración ahora mismo?", "¿Qué crees que aliviaría un poco ese peso?"
                    4. Solo cuando el usuario lo pida, o cuando se haya desahogado lo suficiente, sugiere de forma muy sutil e invitadora una rutina del día o una pausa sensorial sencilla.
                    5. NUNCA utilices frases imperativas, optimismo vacío, ni respuestas robóticas como "Haz esta rutina y estarás bien", "Esto solucionará tu problema", "Debes hacer esto". Deja claro que cada paso se da a su propio ritmo.
                    6. No realices diagnósticos médicos, psicológicos ni menciones tratamientos clínicos. Eres un apoyo espiritual y sensorial de bienestar diario.

                    Usa el nombre $name para referirte con cariño. Mantén tus mensajes breves, cálidos y acogedores, como notas de voz amables.
                """.trimIndent()

                val aiResponse = GeminiRepository.generateResponse(text, systemPrompt)
                val finalMessages = _state.value.chatMessages + ChatMessage("Aura", aiResponse, timeNow)
                _state.value = _state.value.copy(chatMessages = finalMessages, isAuraTyping = false)
            } catch (t: Throwable) {
                t.printStackTrace()
                val fallbackMsg = "Te entiendo perfectamente, ${_state.value.userName.ifEmpty { "Juli" }}. Gracias por expresártelo conmigo. No tienes que resolver todo hoy, podemos tomarlo paso a paso y con mucha suavidad. Si quieres, primero te escucho o conversamos, y luego vemos qué pausa te puede reconfortar."
                val finalMessages = _state.value.chatMessages + ChatMessage("Aura", fallbackMsg, timeNow)
                _state.value = _state.value.copy(chatMessages = finalMessages, isAuraTyping = false)
            }
        }
    }

    fun addToCart(item: ShopItem) {
        val currentCart = _state.value.shoppingCart.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.item.id == item.id }
        if (existingIndex != -1) {
            currentCart[existingIndex].quantity++
        } else {
            currentCart.add(CartItem(item, 1))
        }
        _state.value = _state.value.copy(shoppingCart = currentCart)
    }

    fun removeFromCart(item: ShopItem) {
        val currentCart = _state.value.shoppingCart.toMutableList()
        val index = currentCart.indexOfFirst { it.item.id == item.id }
        if (index != -1) {
            if (currentCart[index].quantity > 1) {
                currentCart[index].quantity--
            } else {
                currentCart.removeAt(index)
            }
        }
        _state.value = _state.value.copy(shoppingCart = currentCart)
    }

    fun simulatePurshase() {
        val cart = _state.value.shoppingCart
        var newKit = _state.value.activeKit
        var newSub = _state.value.activeSubscription
        
        cart.forEach {
            if (it.item.category == "Kit") {
                newKit = "Sí, " + it.item.name
                if (it.item.id == "kit_ritual") {
                    newSub = "Plan Premium ⭐"
                }
            } else if (it.item.category == "Subscription") {
                newSub = it.item.name + " ⭐"
            }
        }

        _state.value = _state.value.copy(
            shoppingCart = emptyList(),
            activeKit = newKit,
            activeSubscription = newSub
        )
    }

    fun openWorkshop(workshop: Workshop) {
        _state.value = _state.value.copy(activeWorkshop = workshop, currentScreen = Screen.WorkshopDetail)
    }

    fun closeActiveWorkshop() {
        _state.value = _state.value.copy(activeWorkshop = null, currentScreen = Screen.Main)
    }

    fun toggleWorkshopLibrary(wId: String) {
        val updated = _state.value.workshopsList.map {
            if (it.id == wId) it.copy(isLibrarySaved = !it.isLibrarySaved) else it
        }
        val currentActive = _state.value.activeWorkshop
        val updatedActive = if (currentActive?.id == wId) {
            updated.find { it.id == wId }
        } else {
            currentActive
        }
        _state.value = _state.value.copy(workshopsList = updated, activeWorkshop = updatedActive)
    }

    fun purchaseWorkshop(workshopId: String) {
        val updated = _state.value.workshopsList.map {
            if (it.id == workshopId) it.copy(isPurchased = true) else it
        }
        _state.value = _state.value.copy(workshopsList = updated)
    }

    fun completeModule(workshopId: String, moduleId: String) {
        val updatedWorkshops = _state.value.workshopsList.map { w ->
            if (w.id == workshopId) {
                val updatedMods = w.modules.map { m ->
                    if (m.id == moduleId) m.copy(isCompleted = true) else m
                }
                w.copy(modules = updatedMods)
            } else w
        }
        val currentActive = _state.value.activeWorkshop
        val updatedActive = if (currentActive?.id == workshopId) {
            updatedWorkshops.find { it.id == workshopId }
        } else {
            currentActive
        }
        _state.value = _state.value.copy(
            workshopsList = updatedWorkshops,
            activeWorkshop = updatedActive
        )
        
        // Recalculate achievement for first workshop completed
        _state.value.workshopsList.forEach { w ->
            if (w.modules.all { it.isCompleted }) {
                unlockAchievement("ach4")
            }
        }
    }

    fun toggleMasterclassLibrary(mcId: String) {
        val updated = _state.value.masterclassesList.map {
            if (it.id == mcId) it.copy(isLibrarySaved = !it.isLibrarySaved) else it
        }
        _state.value = _state.value.copy(masterclassesList = updated)
    }

    fun purchaseMasterclass(mcId: String) {
        val updated = _state.value.masterclassesList.map {
            if (it.id == mcId) it.copy(isPurchased = true) else it
        }
        _state.value = _state.value.copy(masterclassesList = updated)
    }

    fun reserveEvent(eventId: String) {
        val updated = _state.value.eventsList.map {
            if (it.id == eventId) it.copy(isReserved = true) else it
        }
        _state.value = _state.value.copy(eventsList = updated)
    }

    fun registerActivation(actId: String) {
        val updated = _state.value.activationsList.map {
            if (it.id == actId) it.copy(isRegistered = true) else it
        }
        _state.value = _state.value.copy(activationsList = updated)
    }

    fun toggleCorporateBreak() {
        _state.value = _state.value.copy(
            corporateBreakAssigned = !_state.value.corporateBreakAssigned,
            corporateGroupStressLevel = if (_state.value.corporateBreakAssigned) "Normal" else "Alto"
        )
    }

    fun unlockAchievement(id: String) {
        val updated = _state.value.achievementsList.map {
            if (it.id == id) it.copy(isUnlocked = true) else it
        }
        _state.value = _state.value.copy(achievementsList = updated)
    }

    fun updateAgendaTasks(tasks: List<String>) {
        _state.value = _state.value.copy(todayTodoTasks = tasks)
    }

    fun updateRoutineSteps(routineId: String, newSteps: List<String>) {
        if (newSteps.isEmpty()) return
        val updatedRoutines = _state.value.routinesList.map {
            if (it.id == routineId) {
                it.copy(steps = newSteps, currentStepIndex = 0)
            } else it
        }
        _state.value = _state.value.copy(routinesList = updatedRoutines)
    }

    fun confirmInvitation(id: String) {
        val updated = _state.value.invitations.map {
            if (it.id == id) it.copy(isConfirmed = true) else it
        }
        _state.value = _state.value.copy(invitations = updated)
    }

    fun addInvitationFromQR() {
        val scanIndex = _state.value.invitations.size + 1
        val newInvitation = Invitation(
            id = "inv_qr_$scanIndex",
            title = "Invitación Meditación de Sonidos & Cuencos 🌌",
            date = "Viernes Próximo (05 de Junio), 7:00 PM",
            place = "Domo Zen Central, Medellín",
            objective = "Alcanzar el reposo absoluto del sistema nervioso usando frecuencias de cuencos y aromaterapia de lavanda.",
            isConfirmed = false
        )
        _state.value = _state.value.copy(invitations = _state.value.invitations + newInvitation)
    }

    fun saveChatDecision(summary: String, choice: String) {
        val decision = AuraChatDecision(
            dateString = "Hoy - 00:52",
            textSummary = summary,
            decision = choice
        )
        _state.value = _state.value.copy(
            auraChatHistoryDecisions = _state.value.auraChatHistoryDecisions + decision
        )
    }

    fun launchCustomRoutine(themeName: String) {
        val steps = when (themeName) {
            "Estudiar para un examen" -> listOf(
                "Inhala en 4 tiempos, retén 4 y exhala en 4 (repite 3 veces) para calmar el córtex cerebral. 🧠",
                "Pon tu dispositivo móvil en otra habitación o en silencio absoluto para evitar micro-distracciones.",
                "Haz un sorbo lento de agua fresca o de una infusión cítrica de tu Termo MyAura.",
                "Escribe de puño y letra tu único objetivo de estudio prioritario de los próximos 25 minutos.",
                "Inicia tu bloque de enfoque con actitud de serenidad y confianza en tu capacidad mental. ✨"
            )
            "Prepararme para una reunión" -> listOf(
                "Adopta una posición erguida, abre tus hombros y expande el pecho para activar tu postura de seguridad. 🧍",
                "Frota un poco de roll-on relajante en tus muñecas y colócalas cerca de tu rostro para inhalar calma profunda (1 min).",
                "Tararea un tono suave en voz baja para relajar tus cuerdas vocales, tu mandíbula y tus músculos faciales.",
                "Dibuja una sonrisa intencional por 15 segundos; esto manda una señal de seguridad fisiológica a tu amígdala.",
                "Respira suave, asume tu valor intelectual y entra a la sala con pasos conscientes y firmes. Bold!"
            )
            "Conversación intensa" -> listOf(
                "Desplázate físicamente de la zona de conflicto hacia un espacio abierto, un balcón o una ventana libre. 🏞️",
                "Inhala profundamente por la nariz y suelta el aire con un suspiro sonoro con la boca abierta (3 veces).",
                "Moja tus manos y tu nuca con agua fría, sintiendo con atención pura el choque térmico y la frescura de la naturaleza.",
                "Sacude levemente tus manos, tus hombros y tus piernas para liberar el excedente corporal de adrenalina del combate.",
                "Afirma con amabilidad interna: 'Hice lo mejor que pude en ese momento, ahora elijo regresar a mi centro.'"
            )
            "Liberar ira o enfado" -> listOf(
                "Toma tu Objeto anti-estrés del Kit y apriétalo con toda tu fuerza de forma continua por 10 segundos, luego libéralo (repite 3 veces). ✊",
                "Escribe en tu Journal libremente, rayando el papel con toda la velocidad que necesites, sin filtros de censura.",
                "Haz 5 respiraciones rápidas seguidas de exhalaciones lentas por la boca.",
                "Quema o dobla muy pequeño el papel donde vertiste tu enojo como un ritual de desprendimiento.",
                "Espira hondo y recuerda que la ira es solo un mensajero, no tu identidad permanente."
            )
            else -> listOf(
                "Detén todo lo que estés haciendo y observa tu entorno físico inmediato con sutileza. 👀",
                "Identifica y nombra en tu mente 4 cosas de tonalidades pasteles o neutras para anclarte al presente.",
                "Suelta tu mandíbula abriendo ligeramente los labios y baja tus hombros de manera amigable.",
                "Inhala lento sintiendo el aire fresco entrar por tus fosas nasales y expele el aire templado.",
                "Dedica los próximos 2 minutos a percibir únicamente la quietud a tu alrededor."
            )
        }

        val customRoutine = Routine(
            id = "r_extra_${themeName.replace(" ", "_").lowercase()}",
            name = "Rutina Extra: " + themeName,
            recommendedByAura = "Creado para abordar tu estado de $themeName.",
            emotionalState = "Alta Tensión",
            objective = "Brindar calma adaptada antes o después de una situación demandante.",
            durationMinutes = 5,
            kitElementsUsed = emptyList(),
            noKitRequired = true,
            steps = steps,
            isCompleted = false,
            currentStepIndex = 0
        )

        _state.value = _state.value.copy(
            selectedRoutine = customRoutine,
            currentScreen = Screen.ActiveRoutine
        )
    }

    fun resetSession() {
        _state.value = MyAuraState()
    }
}
