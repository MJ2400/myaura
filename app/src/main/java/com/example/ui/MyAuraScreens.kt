package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.*
import com.example.ui.theme.*

@Composable
fun CustomLotusIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawOval(
            color = Color(0xFFC8E6C9), // Mint Green
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.38f, h * 0.2f),
            size = androidx.compose.ui.geometry.Size(w * 0.24f, h * 0.5f)
        )
        drawOval(
            color = Color(0xFFB3E5FC), // Soft Blue
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.2f, h * 0.28f),
            size = androidx.compose.ui.geometry.Size(w * 0.25f, h * 0.42f)
        )
        drawOval(
            color = Color(0xFFE1BEE7), // Soft Lavender / Purple
            topLeft = androidx.compose.ui.geometry.Offset(w * 0.55f, h * 0.28f),
            size = androidx.compose.ui.geometry.Size(w * 0.25f, h * 0.42f)
        )
    }
}

@Composable
fun SleekAmbientBackground(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            if (w > 0f && h > 0f) {
                val r1 = (w * 0.75f).coerceAtLeast(1f)
                // Teal/Mint Top-Left
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFE0F2F1).copy(alpha = 0.55f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(-50f, -50f),
                        radius = r1
                    ),
                    radius = r1,
                    center = androidx.compose.ui.geometry.Offset(-50f, -50f)
                )
                val r2 = (w * 0.85f).coerceAtLeast(1f)
                // Lavender Top-Right
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFF3E5F5).copy(alpha = 0.55f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(w + 50f, -50f),
                        radius = r2
                    ),
                    radius = r2,
                    center = androidx.compose.ui.geometry.Offset(w + 50f, -50f)
                )
                val r3 = (w * 0.95f).coerceAtLeast(1f)
                // Blue Bottom-Right / Right middle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFE3F2FD).copy(alpha = 0.5f), Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset(w + 100f, h * 0.75f),
                        radius = r3
                    ),
                    radius = r3,
                    center = androidx.compose.ui.geometry.Offset(w + 100f, h * 0.75f)
                )
            }
        }
        content()
    }
}

@Composable
fun RoutineCapsule(text: String, bg: Color, textCol: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg.copy(alpha = 0.6f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = textCol)
    }
}

@Composable
fun MyAuraAppContent(viewModel: MyAuraViewModel) {
    val state by viewModel.state.collectAsState()

    // Screen navigation router
    SleekAmbientBackground {
        when (state.currentScreen) {
            Screen.Onboarding -> OnboardingScreen(state, viewModel)
            Screen.Survey -> SurveyScreen(state, viewModel)
            Screen.Main -> MainScreenContainer(state, viewModel)
            Screen.CheckIn -> DayCheckInScreen(state, viewModel)
            Screen.CheckOut -> NightCheckOutScreen(state, viewModel)
            Screen.ActiveRoutine -> ActiveRoutinePlayer(state, viewModel)
            Screen.Chat -> AuraChatScreen(state, viewModel)
            Screen.WorkshopDetail -> WorkshopDetailScreen(state, viewModel)
            Screen.MasterclassesList -> MasterclassesView(state, viewModel)
            Screen.EventsList -> EventsView(state, viewModel)
            Screen.SpecialActivities -> ActionsView(state, viewModel)
            Screen.Calendar -> CalendarView(state, viewModel)
            Screen.Corporate -> CorporateView(state, viewModel)
            Screen.Progress -> ProgressView(state, viewModel)
            Screen.Tienda -> TiendaView(state, viewModel)
        }
    }
}

// ================= ONBOARDING =================
@Composable
fun OnboardingScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    var nameInput by remember { mutableStateOf(state.userName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomLotusIcon(
            modifier = Modifier
                .size(110.dp)
                .padding(bottom = 12.dp)
        )
        Text(
            text = "MyAura",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AuraLavenderPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Hola, soy Aura ✨",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Text(
            text = "Tu guía de bienestar diario e inteligente.",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = "Antes de empezar, quiero conocerte un poco mejor. ¿Cómo quieres que te llame?",
            style = MaterialTheme.typography.bodyMedium,
            color = TextDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            placeholder = { Text("Escribe tu nombre o apodo") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .testTag("username_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextDark,
                unfocusedTextColor = TextDark,
                focusedBorderColor = AuraLavenderPrimary,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Button(
            onClick = {
                if (nameInput.trim().isNotEmpty()) {
                    viewModel.updateUserName(nameInput.trim())
                    viewModel.updateScreen(Screen.Survey)
                }
            },
            enabled = nameInput.trim().isNotEmpty(),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.81f)
                .height(56.dp)
                .testTag("onboarding_continue_button"),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text(text = "Empezar", fontSize = 18.sp, color = Color.White)
        }
    }
}

// ================= SURVEY CONTROLLER =================
@Composable
fun SurveyScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    var step by remember { mutableStateOf(1) }
    val totalSteps = 13
    val name = if (state.userName.isEmpty()) "Juli" else state.userName
    var activeKitToActivate by remember { mutableStateOf("") }
    var showKitActivationQrByStep by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { if (step > 1) step-- else viewModel.updateScreen(Screen.Onboarding) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            LinearProgressIndicator(
                progress = { step.toFloat() / totalSteps },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = AuraLavenderPrimary,
                trackColor = Color.LightGray.copy(0.4f)
            )
            Text(
                text = " $step / $totalSteps",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (step) {
                1 -> SurveyOptionSelector(
                    questionText = "Hola $name ✨, ¿Cuál es tu rango de edad?",
                    options = listOf("<18 años", "18-24 años", "25-34 años", "35-44 años", "45+ años", "Prefiero no decirlo"),
                    selectedValue = state.ageRange,
                    onSelect = { viewModel.setSurveyAnswer(1, it); step++ }
                )
                2 -> SurveyOptionSelector(
                    questionText = "¿Con qué identidad de género te identificas?",
                    options = listOf("Mujer", "Hombre", "No binario", "Prefiero no decirlo"),
                    selectedValue = state.gender,
                    onSelect = { viewModel.setSurveyAnswer(2, it); step++ }
                )
                3 -> SurveyOptionSelector(
                    questionText = "¿Cómo es tu rutina principal diaria actualmente?",
                    options = listOf("Estudio", "Trabajo", "Estudio y Trabajo", "En transición / Busco equilibrio", "Otro"),
                    selectedValue = state.routineDescription,
                    onSelect = { viewModel.setSurveyAnswer(3, it); step++ }
                )
                4 -> MinimalistTimePickerView(
                    questionText = "¿A qué hora te levantas normalmente?",
                    initialTime = state.wakeUpTime,
                    onConfirmed = { viewModel.setSurveyAnswer(4, it); step++ }
                )
                5 -> MinimalistTimePickerView(
                    questionText = "¿A qué hora aproximada terminas tus labores?",
                    initialTime = state.endOfDayTime,
                    onConfirmed = { viewModel.setSurveyAnswer(5, it); step++ }
                )
                6 -> MinimalistTimePickerView(
                    questionText = "¿A qué hora sueles acostarte?",
                    initialTime = state.sleepTime,
                    onConfirmed = { viewModel.setSurveyAnswer(6, it); step++ }
                )
                7 -> SurveyOptionSelector(
                    questionText = "¿Cómo calificarías tu nivel de actividad física?",
                    options = listOf("Voy al gimnasio", "Ejercicio suave o yoga", "Camino mucho en el día", "Casi nada de actividad", "Quiero empezar"),
                    selectedValue = state.workoutHabit,
                    onSelect = { viewModel.setSurveyAnswer(7, it); step++ }
                )
                8 -> SurveyOptionSelector(
                    questionText = "¿Cuál es el nivel de estrés que sientes en un día normal?",
                    options = listOf("Muy estresante", "Pesado", "Moderado", "Leve", "Cambia mucho según el día"),
                    selectedValue = state.dayFeeling,
                    onSelect = { viewModel.setSurveyAnswer(8, it); step++ }
                )
                9 -> SurveySliderSelector(
                    questionText = "En una escala de 1 a 10, ¿Cómo sientes tu carga mental diaria hoy?",
                    currentValue = state.stressScale,
                    onSelect = { viewModel.setSurveyAnswer(9, it); step++ }
                )
                10 -> SurveyMultiSelector(
                    questionText = "¿Qué es lo que más necesitas de MyAura? (Elige las que apliquen)",
                    options = listOf("Manejo del estrés", "Dormir primer paso", "Crear una rutina", "Mayor enfoque", "Acompañamiento emocional", "Detox digital", "Bajar el ritmo", "Bienestar general"),
                    selectedValues = state.goals,
                    onConfirmed = { viewModel.setSurveyAnswer(10, it); step++ }
                )
                11 -> SurveyOptionSelector(
                    questionText = "¿Cuánto tiempo quieres dedicarle a tu rutina de bienestar diaria?",
                    options = listOf("2  minutitos", "3 minutos", "5 minutos", "10 minutos", "15 minutos"),
                    selectedValue = "${state.routineTimeMinutes} minutos",
                    onSelect = { viewModel.setSurveyAnswer(11, it.replace(" minutos", "")); step++ }
                )
                12 -> {
                    if (showKitActivationQrByStep) {
                        QRKitScannerView(
                            kitName = activeKitToActivate,
                            onActivated = { activatedKit ->
                                viewModel.setSurveyAnswer(12, activatedKit)
                                showKitActivationQrByStep = false
                                step++
                            },
                            onCancel = {
                                showKitActivationQrByStep = false
                            }
                        )
                    } else {
                        SurveyOptionSelector(
                            questionText = "¿Tienes alguno de nuestros Kits físicos de bienestar?",
                            options = listOf("Sí, Kit PAUSA 🌸", "Sí, Kit BALANCE 🍀", "Sí, Kit RITUAL 🔮 (Activa Premium)", "No tengo kit todavía"),
                            selectedValue = state.activeKit,
                            onSelect = { selectedOption ->
                                if (selectedOption.startsWith("Sí,")) {
                                    activeKitToActivate = selectedOption
                                    showKitActivationQrByStep = true
                                } else {
                                    viewModel.setSurveyAnswer(12, selectedOption)
                                    step++
                                }
                            }
                        )
                    }
                }
                13 -> SurveyMultiSelector(
                    questionText = "¿En qué momentos del día deseas que Aura te acompañe con pequeñas pausas?",
                    options = listOf("Al despertar", "Antes de empezar a estudiar/trabajar", "En mis descansos", "Después del ejercicio", "Al finalizar mi jornada", "Antes de dormir"),
                    selectedValues = state.auraMoments,
                    onConfirmed = {
                        viewModel.setSurveyAnswer(14, it)
                        step++
                    }
                )
                else -> SurveySummaryView(state, viewModel)
            }
        }
    }
}

@Composable
fun SurveyOptionSelector(
    questionText: String,
    options: List<String>,
    selectedValue: String,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text(text = questionText, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.padding(bottom = 16.dp))
        options.forEach { opt ->
            Card(
                colors = CardDefaults.cardColors(containerColor = if (selectedValue == opt) AuraLavenderBase else Color.White),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, if (selectedValue == opt) AuraLavenderPrimary else Color.LightGray.copy(0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSelect(opt) }
            ) {
                Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedValue == opt, onClick = { onSelect(opt) }, colors = RadioButtonDefaults.colors(selectedColor = AuraLavenderPrimary))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = opt, fontSize = 16.sp, color = TextDark)
                }
            }
        }
    }
}

@Composable
fun QRKitScannerView(
    kitName: String,
    onActivated: (String) -> Unit,
    onCancel: () -> Unit
) {
    var showActivationSuccess by remember { mutableStateOf(false) }

    // Floating Laser animation
    val infiniteTransition = rememberInfiniteTransition(label = "laserAnimation")
    val laserY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!showActivationSuccess) {
            Text(
                text = "Activar Escáner QR 📲",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Apunta la cámara al código QR de activación en la tarjeta holográfica de tu $kitName",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Scanner simulation frame
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Black.copy(0.08f))
                    .border(2.dp, AuraLavenderPrimary.copy(0.4f), RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                // Moving laser lines
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .graphicsLayer(translationY = laserY)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, AuraMintAccent, Color.Transparent)
                            )
                        )
                )

                // Eye icon
                Text(
                    text = "🔍",
                    fontSize = 32.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    showActivationSuccess = true
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Vincular y Escanear Kit ✨", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onCancel) {
                Text("Elegir otro kit / Volver", color = TextSecondary)
            }
        } else {
            // Success activation feedback!
            Text("🔮", fontSize = 64.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "¡Kit Activado Exitosamente! 🎉",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AuraMintAccent
            )
            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                text = "Hemos calibrado tu app para sincronizarse con los elementos físicos aromáticos e interactivos de tu $kitName:",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Accessory confirmation checkmarks
            val list = when {
                kitName.contains("RITUAL") -> listOf("Difusor de aromaterapia USB calibrado ✓", "Esencia de cedro premium vinculada ✓", "Suscripción Premium activada ✓")
                kitName.contains("BALANCE") -> listOf("Vela de soya inteligente emparejada ✓", "Spray relajante sintonizado ✓", "Bitácora sutil sintonizada ✓")
                else -> listOf("Aceites esenciales emparejados ✓", "Roll-on antiestrés calibrado ✓")
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    list.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AuraMintAccent, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item, fontSize = 12.sp, color = TextDark)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onActivated(kitName) },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Continuar Onboarding 🚀", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MinimalistTimePickerView(
    questionText: String,
    initialTime: String,
    onConfirmed: (String) -> Unit
) {
    var hour by remember { mutableStateOf(initialTime.substringBefore(":").toIntOrNull() ?: 7) }
    var min by remember { mutableStateOf(initialTime.substringAfter(":").substringBefore(" ").toIntOrNull() ?: 30) }
    var amPm by remember { mutableStateOf(if (initialTime.contains("PM")) "PM" else "AM") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = questionText, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark, textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 24.dp))
        
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(2.dp, AuraLavenderPrimary),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { if (hour < 12) hour++ else hour = 1 }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Inc")
                    }
                    Text(text = String.format("%02d", hour), fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    IconButton(onClick = { if (hour > 1) hour-- else hour = 12 }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dec")
                    }
                }
                Text(text = " : ", fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { min = (min + 5) % 60 }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Inc")
                    }
                    Text(text = String.format("%02d", min), fontSize = 32.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    IconButton(onClick = { min = if (min >= 5) min - 5 else 55 }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dec")
                    }
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { amPm = if (amPm == "AM") "PM" else "AM" }) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Inc")
                    }
                    Text(text = amPm, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = AuraLavenderPrimary)
                    IconButton(onClick = { amPm = if (amPm == "AM") "PM" else "AM" }) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dec")
                    }
                }
            }
        }

        Button(
            onClick = { onConfirmed(String.format("%02d:%02d %s", hour, min, amPm)) },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.width(180.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text("Confirmar", color = Color.White)
        }
    }
}

@Composable
fun SurveySliderSelector(
    questionText: String,
    currentValue: Int,
    onSelect: (Int) -> Unit
) {
    var valInput by remember { mutableStateOf(currentValue.toFloat()) }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = questionText, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark, textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 24.dp))
        Text(text = "${valInput.toInt()} / 10", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = AuraLavenderPrimary)
        Text(text = if (valInput < 4) "Tranquilo" else if (valInput < 7) "Moderado" else "¡Muy Cargado!", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
        Slider(
            value = valInput,
            onValueChange = { valInput = it },
            valueRange = 1f..10f,
            steps = 8,
            colors = SliderDefaults.colors(thumbColor = AuraLavenderPrimary, activeTrackColor = AuraLavenderPrimary),
            modifier = Modifier.padding(vertical = 24.dp)
        )
        Button(
            onClick = { onSelect(valInput.toInt()) },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.width(180.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text("Siguiente", color = Color.White)
        }
    }
}

@Composable
fun SurveyMultiSelector(
    questionText: String,
    options: List<String>,
    selectedValues: List<String>,
    onConfirmed: (List<String>) -> Unit
) {
    val currentSelected = remember { mutableStateListOf<String>().apply { addAll(selectedValues) } }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = questionText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.padding(bottom = 12.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(options) { opt ->
                val isSelected = currentSelected.contains(opt)
                Card(
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) AuraLavenderBase else Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, if (isSelected) AuraLavenderPrimary else Color.LightGray.copy(0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isSelected) currentSelected.remove(opt) else currentSelected.add(opt)
                        }
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = {
                                if (isSelected) currentSelected.remove(opt) else currentSelected.add(opt)
                            },
                            colors = CheckboxDefaults.colors(checkedColor = AuraLavenderPrimary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = opt, fontSize = 15.sp, color = TextDark)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onConfirmed(currentSelected.toList()) },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text("Confirmar Selección", color = Color.White)
        }
    }
}

@Composable
fun SurveySummaryView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val name = if (state.userName.isEmpty()) "Juli" else state.userName
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "¡Todo preparado, $name! 🔮", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.7f)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, AuraLavenderPrimary.copy(0.3f)),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Aura ha analizado tu perfil:",
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Sueles despertarte a las ${state.wakeUpTime} y acostarte a las ${state.sleepTime}. He calificado tu perfil como 'Buscador de Balance'. Con base en tu kit conectado (${state.activeKit}), he configurado 20 rutinas de bienestar para reducir tu nivel ${state.dayFeeling.lowercase()} de tensión.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.updateScreen(Screen.Main) },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .width(240.dp)
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text("Entrar a mi espacio", fontSize = 16.sp, color = Color.White)
        }
    }
}

// ================= MAIN CONTAINER AND BOTTOM TAB NAVIGATION =================
@Composable
fun MainScreenContainer(state: MyAuraState, viewModel: MyAuraViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White.copy(alpha = 0.85f),
                tonalElevation = 0.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .border(BorderStroke(1.dp, SleekSlate100), RoundedCornerShape(0.dp))
            ) {
                NavigationBarItem(
                    selected = state.currentMainTab == MainTab.Home,
                    onClick = { viewModel.updateMainTab(MainTab.Home) },
                    icon = { Icon(if (state.currentMainTab == MainTab.Home) Icons.Filled.Home else Icons.Outlined.Home, "Inicio") },
                    label = { Text("Inicio", fontWeight = if (state.currentMainTab == MainTab.Home) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SleekSlate900,
                        selectedTextColor = SleekSlate900,
                        indicatorColor = SleekSlate100,
                        unselectedIconColor = SleekSlate400,
                        unselectedTextColor = SleekSlate400
                    )
                )
                NavigationBarItem(
                    selected = state.currentMainTab == MainTab.Explore,
                    onClick = { viewModel.updateMainTab(MainTab.Explore) },
                    icon = { Icon(if (state.currentMainTab == MainTab.Explore) Icons.Filled.Search else Icons.Outlined.Search, "Explorar") },
                    label = { Text("Explorar", fontWeight = if (state.currentMainTab == MainTab.Explore) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SleekSlate900,
                        selectedTextColor = SleekSlate900,
                        indicatorColor = SleekSlate100,
                        unselectedIconColor = SleekSlate400,
                        unselectedTextColor = SleekSlate400
                    )
                )
                NavigationBarItem(
                    selected = state.currentMainTab == MainTab.Profile,
                    onClick = { viewModel.updateMainTab(MainTab.Profile) },
                    icon = { Icon(if (state.currentMainTab == MainTab.Profile) Icons.Filled.Person else Icons.Outlined.Person, "Perfil") },
                    label = { Text("Perfil", fontWeight = if (state.currentMainTab == MainTab.Profile) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SleekSlate900,
                        selectedTextColor = SleekSlate900,
                        indicatorColor = SleekSlate100,
                        unselectedIconColor = SleekSlate400,
                        unselectedTextColor = SleekSlate400
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state.currentMainTab) {
                MainTab.Home -> DashboardView(state, viewModel)
                MainTab.Explore -> ExploreLayout(state, viewModel)
                MainTab.Profile -> ProfileView(state, viewModel)
            }
        }
    }
}

fun getRecommendedRoutinesList(state: MyAuraState): List<Routine> {
    val isNightIn = state.checkOutCompletedToday
    val emotion = if (isNightIn) state.checkOutDayFeel else state.checkInWakeFeeling
    val need = if (isNightIn) state.checkOutNeedsTonight else state.checkInNeedsToday
    val stress = if (isNightIn) state.checkOutMentalLoad else state.checkInStress
    val energy = if (isNightIn) "Baja" else state.checkInEnergy

    val recommendations = mutableListOf<Routine>()

    if (isNightIn) {
        recommendations.add(
            Routine(
                id = "rec_night_1",
                name = "Cierre nocturno profundo 🕯️",
                recommendedByAura = "Pensado para equilibrar la mente antes de dormir.",
                emotionalState = emotion,
                objective = "Separar el día del reposo y inducir sueño reparador.",
                durationMinutes = 5,
                kitElementsUsed = listOf("Vela de soya", "Spray de sienes", "Antifaz suave"),
                noKitRequired = false,
                steps = listOf(
                    "Enciende la vela de soya y contempla la llama respira despacio 1 min.",
                    "Limpia tu frente y esparce el spray de sienes con un suave rodamiento.",
                    "Colócate el antifaz y realiza 5 exhalaciones prolongadas."
                )
            )
        )
        recommendations.add(
            Routine(
                id = "rec_night_2",
                name = "Pausa de desconexión mental 🌙",
                recommendedByAura = "Recomendada por tu nivel de carga mental hoy ($stress/10).",
                emotionalState = emotion,
                objective = "Soltar pensamientos del día y relajar sienes.",
                durationMinutes = 3,
                kitElementsUsed = listOf("Inhalador nasal", "Objeto anti-estrés"),
                noKitRequired = false,
                steps = listOf(
                    "Aleja las pantallas del alcance visual.",
                    "Inhala profundamente tres veces del inhalador nasal.",
                    "Presiona rítmicamente el objeto anti-estrés soltando hombros."
                )
            )
        )
    } else {
        if (stress >= 6 || emotion.contains("estrés") || emotion.contains("Saturada")) {
            recommendations.add(
                Routine(
                    id = "rec_stress_1",
                    name = "Pausa sensorial rápida ⚡",
                    recommendedByAura = "Recomendada por nivel de estrés actual de $stress/10.",
                    emotionalState = emotion,
                    objective = "Bajar tensión inmediata y volver al presente.",
                    durationMinutes = 3,
                    kitElementsUsed = listOf("Inhalador nasal", "Objeto anti-estrés", "Tarjeta de respiración"),
                    noKitRequired = false,
                    steps = listOf(
                        "Usa el inhalador nasal durante tres respiraciones lentas y profundas.",
                        "Aprieta rítmicamente el objeto anti-estrés sintiendo la textura.",
                        "Sigue el patrón rítmico impreso en tu tarjeta de respiración."
                    )
                )
            )
            recommendations.add(
                Routine(
                    id = "rec_stress_2",
                    name = "Reset inmediato con roll-on 🌿",
                    recommendedByAura = "Perfecto para mitigar dolores tensionales y saturación.",
                    emotionalState = emotion,
                    objective = "Regular la respiración de forma guiada.",
                    durationMinutes = 3,
                    kitElementsUsed = listOf("Roll-on de sienes", "Inhalador nasal"),
                    noKitRequired = false,
                    steps = listOf(
                        "Aplica el roll-on de sienes circularmente en sienes y detrás de orejas.",
                        "Inhala despacio por la nariz sintiendo el frescor herbal.",
                        "Exhala liberando todo el aire por la boca entreabierta."
                    )
                )
            )
        } else if (energy == "Baja" || energy == "Muy baja" || emotion.contains("Cansada")) {
            recommendations.add(
                Routine(
                    id = "rec_low_1",
                    name = "Despertar sensorial suave ☕",
                    recommendedByAura = "Seleccionada debido a tu reporte de energía baja hoy.",
                    emotionalState = emotion,
                    objective = "Despertar los sentidos de manera gentil.",
                    durationMinutes = 5,
                    kitElementsUsed = listOf("Termo MyAura", "Inhalador nasal"),
                    noKitRequired = false,
                    steps = listOf(
                        "Sostén tu taza o termo de agua tibia con ambas manos para ganar calor.",
                        "Realiza una inhalación profunda del inhalador para despejar la mente.",
                        "Toma sorbos cortos enfocando tus pensamientos en una intención suave."
                    )
                )
            )
            recommendations.add(
                Routine(
                    id = "rec_low_2",
                    name = "Pausa activa reconstituyente 👣",
                    recommendedByAura = "Ayuda a reactivar el flujo corporal sin sobreesfuerzo.",
                    emotionalState = emotion,
                    objective = "Mover suavemente articulaciones bloqueadas.",
                    durationMinutes = 3,
                    kitElementsUsed = listOf("Objeto anti-estrés"),
                    noKitRequired = true,
                    steps = listOf(
                        "Estira tus brazos hacia arriba inhalando y estira la espalda.",
                        "Mueve suavemente el cuello dibujando círculos en cada dirección.",
                        "Prueba apretar el objeto anti-estrés despertando los dedos."
                    )
                )
            )
        } else {
            recommendations.add(
                Routine(
                    id = "rec_focus_1",
                    name = "Ritual de enfoque del día 🎯",
                    recommendedByAura = "Recomendada para encauzar tu energía con alta concentración.",
                    emotionalState = emotion,
                    objective = "Fijar prioridades y metas del día.",
                    durationMinutes = 5,
                    kitElementsUsed = listOf("Journal premium", "Inhalador nasal"),
                    noKitRequired = false,
                    steps = listOf(
                        "Inhala dos bocanadas del inhalador estimulando el foco cerebral.",
                        "Abre tu diario y anota tu pendiente principal sin presiones.",
                        "Inicia el bloque de trabajo centrado y respirando con calma."
                    )
                )
            )
            recommendations.add(
                Routine(
                    id = "rec_general_1",
                    name = "Organización mental guiada 💫",
                    recommendedByAura = "Ideal para ordenar proyectos matutinos sin abrumarse.",
                    emotionalState = emotion,
                    objective = "Calmar la ansiedad de inicio de jornada.",
                    durationMinutes = 4,
                    kitElementsUsed = listOf("Journal premium"),
                    noKitRequired = true,
                    steps = listOf(
                        "Cierra los ojos y haz 3 respiraciones diafragmáticas profundas.",
                        "Anota tu pendiente más pesado y divídelo en dos partes pequeñas.",
                        "Escribe una frase de agradecimiento para sintonizar en positivo."
                    )
                )
            )
        }
    }

    if (recommendations.isEmpty()) {
        recommendations.add(StaticData.independentRoutines.first())
    }
    return recommendations
}

// ================= THE DASHBOARD =================
@Composable
fun DashboardView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val name = if (state.userName.isEmpty()) "Juli" else state.userName
    val localScrollState = rememberScrollState()
    var showRoutineCustomizerDialog by remember { mutableStateOf(false) }

    val quoteList = listOf(
        "Una pausa también es avanzar. Respira hondo ✨",
        "Regálate un momento para habitar tu presente hoy.",
        "Tu paz de espíritu no es negociable.",
        "Avanzar despacio sigue siendo avanzar."
    )
    val randomQuote = remember { quoteList.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(localScrollState)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 24.dp)
            .statusBarsPadding()
    ) {
        // --- Sleek Header ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, SleekSlate200, CircleShape)
                    .clickable { /* Side menu */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = SleekSlate600,
                    modifier = Modifier.size(20.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFFA7FFEB), Color(0xFFE1BEE7), Color(0xFFBBDEFB))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.White.copy(0.8f)))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MyAura",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = SleekSlate800,
                    letterSpacing = (-0.5).sp
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SleekSlate200)
                    .border(2.dp, Color.White, CircleShape)
            ) {
                Text(
                    text = name.take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = SleekSlate700,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 14.sp
                )
            }
        }

        // --- Greetings ---
        Text(
            text = "Hola, $name ✨",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = SleekSlate800,
            letterSpacing = (-1).sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = "“$randomQuote”",
            fontSize = 14.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
            color = SleekSlate500,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // --- Morning & Night Check-Ins Grid ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Morning Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(0.85f))
                    .border(1.dp, SleekSlate200, RoundedCornerShape(24.dp))
                    .clickable { viewModel.updateScreen(Screen.CheckIn) }
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SleekTealBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("☀️", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "MAÑANA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate400,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = if (state.checkInCompletedToday) "Check-in realizado" else "Check-in pendiente",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SleekSlate700
                    )
                }
            }

            // Night Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(SleekIndigoNight)
                    .border(1.dp, SleekSlate800, RoundedCornerShape(24.dp))
                    .clickable { viewModel.updateScreen(Screen.CheckOut) }
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🌙", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "NOCHE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate300,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = if (state.checkOutCompletedToday) "Check-out realizado" else "Check-out pendiente",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // --- Routine Box ---
        if (state.checkInCompletedToday || state.checkOutCompletedToday) {
            var routinesExpanded by remember { mutableStateOf(false) }
            val recommendations = getRecommendedRoutinesList(state)
            
            // Edit routine inline fields
            var editingRoutineId by remember { mutableStateOf<String?>(null) }
            var editingStepsText by remember { mutableStateOf("") }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(32.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tu rutina está aquí ✨",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate800
                        )
                        IconButton(onClick = { routinesExpanded = !routinesExpanded }) {
                            Icon(
                                imageVector = if (routinesExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Desplegar",
                                tint = SleekSlate600
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { 
                                routinesExpanded = !routinesExpanded
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .weight(1.3f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900)
                        ) {
                            Text(
                                if (routinesExpanded) "Ocultar recomendaciones" else "Iniciar rutina (Desplegar)", 
                                color = Color.White, 
                                fontWeight = FontWeight.Bold, 
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(SleekSlate100)
                                .clickable { showRoutineCustomizerDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Personalizar",
                                tint = SleekSlate500,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    if (routinesExpanded) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Recomendadas según tus respuestas hoy:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AuraLavenderPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        recommendations.forEach { routine ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                                border = BorderStroke(1.dp, SleekSlate100),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = routine.name, 
                                                fontWeight = FontWeight.Bold, 
                                                fontSize = 14.sp, 
                                                color = SleekSlate800
                                            )
                                            Text(
                                                text = "Objetivo: ${routine.objective}", 
                                                fontSize = 11.sp, 
                                                color = SleekSlate600,
                                                modifier = Modifier.padding(vertical = 2.dp)
                                            )
                                            Text(
                                                text = "Duración: ${routine.durationMinutes} min", 
                                                fontSize = 11.sp, 
                                                fontWeight = FontWeight.SemiBold,
                                                color = AuraLavenderPrimary
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Aura Pick",
                                            tint = AuraLavenderPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Productos en kit:", 
                                        fontSize = 11.sp, 
                                        fontWeight = FontWeight.SemiBold, 
                                        color = SleekSlate700
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        routine.kitElementsUsed.forEach { prod ->
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(AuraLavenderBase.copy(0.4f))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(prod, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Pasos:", 
                                        fontSize = 11.sp, 
                                        fontWeight = FontWeight.SemiBold, 
                                        color = SleekSlate700
                                    )
                                    routine.steps.forEachIndexed { idx, stp ->
                                        Text(
                                            text = "${idx + 1}. $stp", 
                                            fontSize = 11.sp, 
                                            color = SleekSlate600,
                                            modifier = Modifier.padding(vertical = 1.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    if (editingRoutineId == routine.id) {
                                        OutlinedTextField(
                                            value = editingStepsText,
                                            onValueChange = { editingStepsText = it },
                                            placeholder = { Text("Escribe un paso en cada línea...") },
                                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp)
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Button(
                                                onClick = {
                                                    val newSteps = editingStepsText.lines().filter { it.isNotBlank() }
                                                    viewModel.updateRoutineSteps(routine.id, newSteps)
                                                    editingRoutineId = null
                                                },
                                                modifier = Modifier.weight(1f).height(36.dp),
                                                colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent)
                                            ) {
                                                Text("Guardar pasos", color = Color.White, fontSize = 11.sp)
                                            }
                                            TextButton(
                                                onClick = { editingRoutineId = null },
                                                modifier = Modifier.height(36.dp)
                                            ) {
                                                Text("Cancelar", color = Color.Red, fontSize = 11.sp)
                                            }
                                        }
                                    } else {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Button(
                                                onClick = { viewModel.selectRoutine(routine) },
                                                colors = ButtonDefaults.buttonColors(containerColor = SleekSlate800),
                                                modifier = Modifier.weight(1f).height(34.dp),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("Seleccionar", color = Color.White, fontSize = 10.sp)
                                            }
                                            OutlinedButton(
                                                onClick = {
                                                    editingRoutineId = routine.id
                                                    editingStepsText = routine.steps.joinToString("\n")
                                                },
                                                border = BorderStroke(1.dp, SleekSlate300),
                                                modifier = Modifier.weight(1f).height(34.dp),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text("Editar rutina", color = SleekSlate700, fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Smart Tracker Slates Section (Salud Espiritual Hoy) ---
        Text(
            text = "Tu salud espiritual hoy:",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = SleekSlate800,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        if (!state.checkInCompletedToday) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Haz tu check-in para conocer tu estado de hoy ✨", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate800)
                    Text(
                        text = "Aura necesita saber cómo dormiste, cómo está tu energía y qué tan cargado está tu día para mostrarte tu resumen personalizado.",
                        fontSize = 11.sp,
                        color = SleekSlate500,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )
                    Button(
                        onClick = { viewModel.updateScreen(Screen.CheckIn) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Hacer check-in 🧠", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                // Determine sleep text based on check-in duration
                val sleepText = when (state.checkInSleepDuration) {
                    "Menos de 4 horas" -> "Dormiste menos de 4 h"
                    "4 a 5 horas" -> "Dormiste entre 4 y 5 h"
                    "6 a 7 horas" -> "Dormiste entre 6 y 7 h"
                    "8 horas o más" -> "Dormiste 8 h o más"
                    "No estoy segura/o" -> "Sueño no estimado"
                    else -> "Dormiste ${state.checkInSleepDuration}"
                }
                
                item { DialPill("Sueño 🌙", sleepText) }
                item { DialPill("Estrés ⚡", "Estrés: ${state.checkInStress}/10") }
                item { DialPill("Energía 🔋", "Energía ${state.checkInEnergy.lowercase()}") }
                item { DialPill("Enfoque 🎯", "Necesitas ${state.checkInNeedsToday.lowercase()}") }
            }
        }

        // --- Today's To-Dos Card ("Tu plan de hoy") ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.82f)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Tu plan de hoy ✨", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SleekSlate800, modifier = Modifier.padding(bottom = 8.dp))
                
                if (state.checkOutCompletedToday) {
                    Text(
                        text = "Tu día quedó cerrado, ${if (state.userName.isEmpty()) "Juli" else state.userName} ✨. Mañana podrás escribir un nuevo plan.",
                        fontSize = 13.sp,
                        color = SleekSlate600,
                        fontStyle = FontStyle.Italic
                    )
                } else if (state.checkInCompletedToday && state.rawAgendaText.isNotEmpty()) {
                    // Display exact user entry
                    Text(
                        text = state.rawAgendaText,
                        fontSize = 13.sp,
                        color = SleekSlate700,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    Text("Aún no has escrito tu rutina de hoy.", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = SleekSlate700)
                    Text(
                        text = "Puedes escribir tus pendientes en el check-in para que Aura te ayude a organizar mejor tus pausas.",
                        fontSize = 12.sp,
                        color = SleekSlate500,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )
                    Button(
                        onClick = { viewModel.updateScreen(Screen.CheckIn) },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Hacer check-in ✍️", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }

        // --- Chat IA Card ---
        Card(
            onClick = { viewModel.updateScreen(Screen.Chat) },
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🔮", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Aura IA Emocional 💬", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                    Text("Habla libremente con Aura o pide consejos personalizados.", fontSize = 12.sp, color = SleekSlate500)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Additional Routine Section ---
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "¿Quieres hacer una rutina adicional? 🎯",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
                Text(
                    text = "Selecciona un tema y Aura preparará una rutina de calma adaptada al instante:",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                val themes = listOf(
                    "Estudiar para un examen" to "📚",
                    "Prepararme para una reunión" to "💼",
                    "Conversación intensa" to "🗣️",
                    "Liberar ira o enfado" to "🔥"
                )

                themes.forEach { (themeName, emoji) ->
                    Button(
                        onClick = { viewModel.launchCustomRoutine(themeName) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate50),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, SleekSlate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(40.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(themeName, color = SleekSlate800, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                            Text(emoji, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        if (showRoutineCustomizerDialog) {
            RoutineCustomizerDialog(state, viewModel) {
                showRoutineCustomizerDialog = false
            }
        }
    }
}

@Composable
fun DialPill(title: String, desc: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, SleekSlate200),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = SleekSlate800)
            Text(desc, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

// ================= EXPLORE VIEW CATEGORY GRID =================
@Composable
fun ExploreLayout(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Text("Explorar Bienestar 🔮", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
        Text("Profundiza en tu paz espiritual, compra y haz seguimiento.", style = MaterialTheme.typography.bodyMedium, color = TextSecondary, modifier = Modifier.padding(bottom = 16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = AuraLavenderPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tienda MyAura (COP)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text("Compra Kits físicos, repuestos de aceites, velas y suscripciones de bienestar en Pesos Colombianos.", fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.updateScreen(Screen.Tienda) },
                    colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Ingresar a Tienda 🛒", color = Color.White)
                }
            }
        }

        Text("Servicios e Integraciones", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryGridItem(
                    label = "Suscripciones 🚀",
                    sub = "Administra planes",
                    iconString = "⭐",
                    onClick = { viewModel.updateScreen(Screen.Tienda) },
                    modifier = Modifier.weight(1f)
                )
                CategoryGridItem(
                    label = "Talleres 📚",
                    sub = "Progreso: 2/6",
                    iconString = "📚",
                    onClick = { viewModel.updateScreen(Screen.WorkshopDetail) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryGridItem(
                    label = "Masterclasses 🎬",
                    sub = "Visto: 3/10",
                    iconString = "🎬",
                    onClick = { viewModel.updateScreen(Screen.MasterclassesList) },
                    modifier = Modifier.weight(1f)
                )
                CategoryGridItem(
                    label = "Eventos Presenciales 📍",
                    sub = "Medellín",
                    iconString = "📍",
                    onClick = { viewModel.updateScreen(Screen.EventsList) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CategoryGridItem(
                    label = "MyAura Activaciones 🌟",
                    sub = "Con invitación QR",
                    iconString = "🌟",
                    onClick = { viewModel.updateScreen(Screen.SpecialActivities) },
                    modifier = Modifier.weight(1f)
                )
                CategoryGridItem(
                    label = "Calendario 📅",
                    sub = "Organiza tu pausa",
                    iconString = "📅",
                    onClick = { viewModel.updateScreen(Screen.Calendar) },
                    modifier = Modifier.weight(1f)
                )
            }
            CategoryGridItem(
                label = "Canal Corporativo 🏢",
                sub = "Para empresas, universidades o equipos",
                iconString = "🏢",
                onClick = { viewModel.updateScreen(Screen.Corporate) },
                modifier = Modifier.fillMaxWidth()
            )
            CategoryGridItem(
                label = "Progreso y Logros 📊",
                sub = "Miras tus estadísticas y medallas",
                iconString = "📊",
                onClick = { viewModel.updateScreen(Screen.Progress) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CategoryGridItem(label: String, sub: String, iconString: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(0.3f)),
        modifier = modifier.height(100.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.Center) {
            Text(iconString, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(sub, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

// ================= PROFILE VIEW =================
@Composable
fun ProfileView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AuraLavenderBase),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(AuraLavenderPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.userName.isEmpty()) "J" else state.userName.take(1).uppercase(),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(if (state.userName.isEmpty()) "Julián Restrepo ✨" else state.userName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark)
                    Text("Suscripción activa: ${state.activeSubscription}", color = AuraLavenderPrimary, fontWeight = FontWeight.Bold)
                    Text("Kit actual: ${state.activeKit}", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }

        Text("Componentes de tu Kit de Bienestar:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val components = when {
                    state.activeKit.contains("RITUAL") -> listOf("Difusor de aromaterapia USB 💨", "Aceite esencial extra premium (Cedro) 🪵", "Vela grande doble mecha 🕯️", "Masajeador de cabeza capilar 💆", "Antifaz suave protector de luz 👁️", "Termo MyAura 350ml 🥛")
                    state.activeKit.contains("BALANCE") -> listOf("Vela aromática de soya 🕯️", "Spray de almohada relajante 🌌", "Aceite esencial blend (Lavanda/Bergamota) 🌸", "Objeto antiestrés premium 🌟", "Journal sutil de hojas limpias 📝")
                    else -> listOf("Inhalador nasal aromático 👃", "Roll-on anti-tensiones 10ml 🌱", "Tarjetas de respiración rítmica 🎴", "Objeto blando antiestrés 🎾", "Bolsita de tela MyAura 🎒", "Plantilla de tracking de hábitos 📌")
                }
                components.forEach { item ->
                    Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = AuraMintAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(item, fontSize = 13.sp, color = TextDark)
                    }
                }
            }
        }

        Text("Tus Logros e Hitos:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        state.achievementsList.forEach { ach ->
            Card(
                colors = CardDefaults.cardColors(containerColor = if (ach.isUnlocked) AuraMintLight else Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(if (ach.isUnlocked) "🏆" else "🔒", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(ach.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(ach.description, fontSize = 11.sp, color = TextSecondary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { showLogoutDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión", color = Color.White)
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("¿Deseas cerrar sesión?") },
                text = { Text("Se restablecerán las configuraciones locales y el progreso en memoria de MyAura.") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.resetSession()
                        showLogoutDialog = false
                    }) {
                        Text("Aceptar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

// ================= CHECK-IN DEL DIA =================
@Composable
fun DayCheckInScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    var checkWakeFeeling by remember { mutableStateOf("Tranquila/o") }
    var checkSleepDuration by remember { mutableStateOf("6 a 7 horas") }
    var checkSleepQuality by remember { mutableStateOf("Dormí bien") }
    var checkEnergy by remember { mutableStateOf("Media") }
    var checkStressFloat by remember { mutableStateOf(5f) }
    val checkStress = checkStressFloat.toInt()
    var checkDayLoad by remember { mutableStateOf("Normal") }
    var checkNeedsToday by remember { mutableStateOf("Calma") }
    var agendaText by remember { mutableStateOf("") }

    val name = if (state.userName.isEmpty()) "Julián" else state.userName

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBFBFE))
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = { viewModel.updateScreen(Screen.Main) },
                modifier = Modifier.background(Color.White, CircleShape).border(1.dp, SleekSlate100, CircleShape)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = SleekSlate700)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text("Check-In Matutino 🌅", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(0.25f)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("✨", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Hola, $name ✨ ¿Cómo amaneciste hoy?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate800
                        )
                    }
                }
            }

            // --- Question 1 ---
            item {
                CheckInQuestionCard(title = "1. ¿Cómo amaneciste hoy?") {
                    val opts = listOf("Tranquila/o", "Cansada/o", "Con estrés", "Desanimada/o", "Ansiosa/o", "Motivada/o", "Saturada/o", "No sé muy bien")
                    FlowWrap(opts, checkWakeFeeling) { checkWakeFeeling = it }
                }
            }

            // --- Question 2 ---
            item {
                CheckInQuestionCard(title = "2. ¿Cuántas horas dormiste aproximadamente?") {
                    val opts = listOf("Menos de 4 horas", "4 a 5 horas", "6 a 7 horas", "8 horas o más", "No estoy segura/o")
                    FlowWrap(opts, checkSleepDuration) { checkSleepDuration = it }
                }
            }

            // --- Question 3 ---
            item {
                CheckInQuestionCard(title = "3. ¿Cómo fue la calidad de tu sueño?") {
                    val opts = listOf("Dormí muy bien", "Dormí bien", "Dormí regular", "Me desperté varias veces", "Tuve sueños intensos o pesadillas", "Siento que no descansé")
                    FlowWrap(opts, checkSleepQuality) { checkSleepQuality = it }
                }
            }

            // --- Question 4 ---
            item {
                CheckInQuestionCard(title = "4. ¿Cómo está tu energía ahora mismo?") {
                    val opts = listOf("Alta", "Media", "Baja", "Muy baja")
                    FlowWrap(opts, checkEnergy) { checkEnergy = it }
                }
            }

            // --- Question 5 ---
            item {
                CheckInQuestionCard(title = "5. ¿Cuál es tu nivel de estrés en este momento?") {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Calma (1)", fontSize = 11.sp, color = SleekSlate500)
                            Text("$checkStress / 10", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                            Text("Máximo (10)", fontSize = 11.sp, color = SleekSlate500)
                        }
                        Slider(
                            value = checkStressFloat,
                            onValueChange = { checkStressFloat = it },
                            valueRange = 1f..10f,
                            steps = 8,
                            colors = SliderDefaults.colors(
                                thumbColor = AuraLavenderPrimary,
                                activeTrackColor = AuraLavenderPrimary,
                                inactiveTrackColor = SleekSlate100
                            )
                        )
                    }
                }
            }

            // --- Question 6 ---
            item {
                CheckInQuestionCard(title = "6. ¿Qué tan cargado se ve tu día?") {
                    val opts = listOf("Muy cargado", "Algo cargado", "Normal", "Ligero", "No sé todavía")
                    FlowWrap(opts, checkDayLoad) { checkDayLoad = it }
                }
            }

            // --- Question 7 ---
            item {
                CheckInQuestionCard(title = "7. ¿Qué necesitas más hoy?") {
                    val opts = listOf("Calma", "Enfoque", "Energía", "Organización", "Descanso", "Desahogarme", "Empezar suave")
                    FlowWrap(opts, checkNeedsToday) { checkNeedsToday = it }
                }
            }

            // --- Question 8 ---
            item {
                CheckInQuestionCard(title = "8. ¿Qué tienes que hacer hoy?") {
                    Column {
                        Text(
                            "Escribe una lista corta con horas aproximadas o pendientes.",
                            fontSize = 11.sp,
                            color = SleekSlate500,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = agendaText,
                            onValueChange = { agendaText = it },
                            placeholder = { Text("Ej: 8:00 a.m. clase, 11:00 a.m. trabajo, 2:00 p.m. entrenar…") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextDark,
                                unfocusedTextColor = TextDark,
                                focusedBorderColor = AuraLavenderPrimary,
                                unfocusedBorderColor = SleekSlate200
                            )
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Button(
                onClick = {
                    viewModel.completeCheckIn(
                        energy = checkEnergy,
                        stress = checkStress,
                        wakeFeeling = checkWakeFeeling,
                        sleepQuality = checkSleepQuality,
                        sleepDuration = checkSleepDuration,
                        dreams = "",
                        wakeRested = "",
                        needsToday = checkNeedsToday,
                        remindPause = false,
                        todosList = emptyList(),
                        agendaText = agendaText,
                        dayLoad = checkDayLoad
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
            ) {
                Text("Guardar y Cerrar Check-In ✓", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

// ================= CHECK-OUT DE LA NOCHE =================
@Composable
fun NightCheckOutScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    var checkFinishedDay by remember { mutableStateOf("Sí, ya estoy en casa") }
    var checkDayFeel by remember { mutableStateOf("Tranquila/o") }
    var checkHeaviestThing by remember { mutableStateOf("Nada en especial") }
    var checkMentalLoadFloat by remember { mutableStateOf(5f) }
    val checkMentalLoad = checkMentalLoadFloat.toInt()
    var checkLetGo by remember { mutableStateOf("No mucho") }
    var checkBodyStatus by remember { mutableStateOf("Relajado") }
    var checkNeedsTonight by remember { mutableStateOf("Relajarme") }
    var checkFinishedPendings by remember { mutableStateOf("Sí, terminé todo") }

    val name = if (state.userName.isEmpty()) "Julián" else state.userName

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9FD))
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = { viewModel.updateScreen(Screen.Main) },
                modifier = Modifier.background(Color.White, CircleShape).border(1.dp, SleekSlate100, CircleShape)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = SleekSlate700)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text("Cierre de Noche 🌙", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = SleekIndigoNight.copy(0.12f)),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekIndigoNight.copy(0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("✨", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "¿Ya finalizaste tu día, $name?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate800
                        )
                    }
                }
            }

            // --- Question 1 ---
            item {
                CheckInQuestionCard(title = "1. ¿Ya finalizaste tu día?") {
                    val opts = listOf("Sí, ya estoy en casa", "Sí, pero sigo activa/o", "No todavía", "Quiero hacer una pausa antes de dormir")
                    FlowWrap(opts, checkFinishedDay) { checkFinishedDay = it }
                }
            }

            // --- Question 2 ---
            item {
                CheckInQuestionCard(title = "2. ¿Cómo te sientes al cerrar el día?") {
                    val opts = listOf("Tranquila/o", "Cansada/o", "Frustrada/o", "Saturada/o", "Agradecida/o", "Triste", "En paz", "No sé muy bien")
                    FlowWrap(opts, checkDayFeel) { checkDayFeel = it }
                }
            }

            // --- Question 3 ---
            item {
                CheckInQuestionCard(title = "3. ¿Qué fue lo más pesado de hoy?") {
                    val opts = listOf("Estudio", "Trabajo", "Relaciones/personas", "Pensamientos", "Cansancio físico", "Pendientes acumulados", "Nada en especial", "Prefiero escribirlo")
                    FlowWrap(opts, checkHeaviestThing) { checkHeaviestThing = it }
                }
            }

            // --- Question 4 ---
            item {
                CheckInQuestionCard(title = "4. ¿Qué nivel de carga mental tienes ahora?") {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Baja (1)", fontSize = 11.sp, color = SleekSlate500)
                            Text("$checkMentalLoad / 10", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                            Text("Máxima (10)", fontSize = 11.sp, color = SleekSlate500)
                        }
                        Slider(
                            value = checkMentalLoadFloat,
                            onValueChange = { checkMentalLoadFloat = it },
                            valueRange = 1f..10f,
                            steps = 8,
                            colors = SliderDefaults.colors(
                                thumbColor = AuraLavenderPrimary,
                                activeTrackColor = AuraLavenderPrimary,
                                inactiveTrackColor = SleekSlate100
                            )
                        )
                    }
                }
            }

            // --- Question 5 ---
            item {
                CheckInQuestionCard(title = "5. ¿Sientes que necesitas soltar algo antes de dormir?") {
                    val opts = listOf("Pensamientos", "Emociones", "Pendientes", "Tensión del cuerpo", "No mucho", "No sé")
                    FlowWrap(opts, checkLetGo) { checkLetGo = it }
                }
            }

            // --- Question 6 ---
            item {
                CheckInQuestionCard(title = "6. ¿Cómo está tu cuerpo en este momento?") {
                    val opts = listOf("Relajado", "Tenso", "Cansado", "Inquieto", "Pesado", "Con energía todavía")
                    FlowWrap(opts, checkBodyStatus) { checkBodyStatus = it }
                }
            }

            // --- Question 7 ---
            item {
                CheckInQuestionCard(title = "7. ¿Qué necesitas para cerrar mejor la noche?") {
                    val opts = listOf("Relajarme", "Soltar pensamientos", "Agradecer el día", "Escribir lo que siento", "Prepararme para dormir", "Desconectarme del celular", "Calmar ansiedad", "Cerrar pendientes")
                    FlowWrap(opts, checkNeedsTonight) { checkNeedsTonight = it }
                }
            }

            // --- Question 8 ---
            item {
                CheckInQuestionCard(title = "8. ¿Lograste terminar todos los pendientes de hoy?") {
                    val opts = listOf("Sí, terminé todo", "Terminé la mayoría", "Me faltaron algunas cosas", "No pude avanzar mucho", "Prefiero revisarlo mañana")
                    FlowWrap(opts, checkFinishedPendings) { checkFinishedPendings = it }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Button(
                onClick = {
                    viewModel.completeCheckOut(
                        dayFeel = checkDayFeel,
                        needsTonight = checkNeedsTonight,
                        finishedPendingsOption = checkFinishedPendings,
                        notesForTomorrow = "",
                        finishedDay = checkFinishedDay,
                        heaviestThing = checkHeaviestThing,
                        mentalLoad = checkMentalLoad,
                        letGo = checkLetGo,
                        bodyStatus = checkBodyStatus,
                        finishedPendings = checkFinishedPendings
                    )
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
            ) {
                Text("Finalizar Check-Out 🌙", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun CheckInQuestionCard(title: String, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, SleekSlate100),
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate800)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun FlowWrap(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    val chunked = options.chunked(2)
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        chunked.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { opt ->
                    val isSelected = opt == selected
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) AuraLavenderPrimary else Color.White)
                            .border(1.dp, if (isSelected) AuraLavenderPrimary else SleekSlate200, RoundedCornerShape(16.dp))
                            .clickable { onSelect(opt) }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = opt,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else SleekSlate700,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

// ================= CHAT CON AURA =================
@Composable
fun AuraChatScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    var txtInput by remember { mutableStateOf("") }
    val chatsScrollState = rememberScrollState()
    var showSessionSummaryDialog by remember { mutableStateOf(false) }
    var expandedDecisionsHistory by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Close")
            }
            CustomLotusIcon(modifier = Modifier.size(34.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Aura IA Emocional 🔮", fontWeight = FontWeight.Bold)
                Text("En línea y lista para escucharte", fontSize = 11.sp, color = AuraMintAccent)
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { showSessionSummaryDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(34.dp)
            ) {
                Text("Finalizar 🏁", fontSize = 11.sp, color = Color.White)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF9C4))
                .padding(8.dp)
        ) {
            Text(
                text = "⚠️ MyAura acompaña tu bienestar diario pero no reemplaza atención psicológica profesional.",
                fontSize = 11.sp,
                color = Color.DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // Expanded Save Log indicator banner
        if (state.auraChatHistoryDecisions.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedDecisionsHistory = !expandedDecisionsHistory },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📝 Bitácora de Decisiones con Aura (${state.auraChatHistoryDecisions.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(if (expandedDecisionsHistory) "Cerrar ▲" else "Ver historial ▼", fontSize = 11.sp, color = AuraLavenderPrimary)
                    }
                    if (expandedDecisionsHistory) {
                        Spacer(modifier = Modifier.height(8.dp))
                        state.auraChatHistoryDecisions.forEach { entry ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = BorderStroke(1.dp, SleekSlate200),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text("Estadia: ${entry.decision}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                                        Text(entry.dateString, fontSize = 9.sp, color = TextSecondary)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text("Consejo guardado: \"${if (entry.textSummary.length > 60) entry.textSummary.take(57) + "..." else entry.textSummary}\"", fontSize = 10.sp, color = TextSecondary, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(chatsScrollState)
                .padding(16.dp)
        ) {
            state.chatMessages.forEach { msg ->
                val isMe = msg.sender == "User"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = if (isMe) AuraBlueAccent else Color.White),
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = if (isMe) 20.dp else 0.dp,
                            bottomEnd = if (isMe) 0.dp else 20.dp
                        ),
                        modifier = Modifier.fillMaxWidth(0.82f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = msg.text, fontSize = 14.sp, color = if (isMe) Color.White else TextDark)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = msg.time, fontSize = 9.sp, color = if (isMe) Color.White.copy(alpha = 0.7f) else Color.Gray, modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }

            if (state.isAuraTyping) {
                Text(
                    text = "Aura está sintiendo tu energía... 🔮",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            val lastMsgIsAura = state.chatMessages.lastOrNull()?.sender == "Aura"
            if (lastMsgIsAura && !state.isAuraTyping) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¿Cómo te gustaría proceder con tu rutina de hoy?",
                        fontSize = 12.sp,
                        color = SleekSlate500,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val adviceText = state.chatMessages.lastOrNull { it.sender == "Aura" }?.text ?: "Conversación amigable con Aura."
                                val recommendedRoutine = state.routinesList.firstOrNull() ?: StaticData.independentRoutines.first()
                                viewModel.saveChatDecision(adviceText, "Aceptó rutina: ${recommendedRoutine.name}")
                                viewModel.selectRoutine(recommendedRoutine)
                            },
                            modifier = Modifier.weight(1f).height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Aceptar Rutina Sugerida ✨", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Button(
                            onClick = {
                                val adviceText = state.chatMessages.lastOrNull { it.sender == "Aura" }?.text ?: "Conversación amigable con Aura."
                                viewModel.saveChatDecision(adviceText, "Ya se siente mejor 😊")
                                viewModel.updateScreen(Screen.Main)
                            },
                            modifier = Modifier.weight(1f).height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraMintLight),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Ya me siento mejor 😊", color = AuraMintAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = txtInput,
                onValueChange = { txtInput = it },
                placeholder = { Text("¿Cómo te sientes en este momento?", fontSize = 13.sp) },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AuraLavenderPrimary,
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (txtInput.trim().isNotEmpty()) {
                        viewModel.sendMessageToAura(txtInput.trim())
                        txtInput = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(AuraLavenderPrimary, CircleShape)
            ) {
                Icon(Icons.Default.Send, contentDescription = "Enviar", tint = Color.White)
            }
        }
    }

    // Closing Dialog when user taps top "Finalizar" button
    if (showSessionSummaryDialog) {
        val lastAuraMsg = state.chatMessages.findLast { it.sender == "Aura" }?.text ?: "Espero que te sientas muy bien."
        val recommendedRoutine = state.routinesList.firstOrNull() ?: StaticData.independentRoutines.first()

        androidx.compose.ui.window.Dialog(
            onDismissRequest = { showSessionSummaryDialog = false }
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Cierre de Sesión 🔮", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text("Aura te aconseja y te sugiere realizar:", fontSize = 12.sp, color = TextSecondary, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(0.3f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (lastAuraMsg.length > 120) lastAuraMsg.take(117) + "..." else lastAuraMsg,
                            fontSize = 12.sp,
                            color = TextDark,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("✨ Rutina recomendada:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AuraLavenderPrimary)
                    Text(recommendedRoutine.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                    Text("(${recommendedRoutine.durationMinutes} min)", fontSize = 11.sp, color = TextSecondary)

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.saveChatDecision(lastAuraMsg, "Aceptó rutina: ${recommendedRoutine.name}")
                            viewModel.selectRoutine(recommendedRoutine)
                            showSessionSummaryDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text("Aceptar Rutina Sugerida", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.saveChatDecision(lastAuraMsg, "Ya me siento mejor 😊")
                            showSessionSummaryDialog = false
                            viewModel.updateScreen(Screen.Main)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate100),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                    ) {
                        Text("Ya me siento mejor 😊", color = SleekSlate800)
                    }
                }
            }
        }
    }
}

// ================= ACTIVE ROUTINE PLAYER =================
@Composable
fun ActiveRoutinePlayer(state: MyAuraState, viewModel: MyAuraViewModel) {
    val routine = state.selectedRoutine ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text("Actividad en curso 🧘", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = routine.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
            Text(text = "Duración estimada: ${routine.durationMinutes} minutos", fontSize = 14.sp, color = TextSecondary)
            
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Paso actual:", fontWeight = FontWeight.SemiBold, color = TextSecondary)
                    Text(
                        text = if (routine.steps.isNotEmpty() && routine.currentStepIndex in routine.steps.indices) {
                            routine.steps[routine.currentStepIndex]
                        } else {
                            "Paso en curso..."
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    Text(
                        text = "Paso ${if (routine.steps.isNotEmpty()) (routine.currentStepIndex + 1).coerceAtMost(routine.steps.size) else 0} de ${routine.steps.size}",
                        fontSize = 12.sp,
                        color = AuraMintAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.tickRoutineStep() },
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary)
        ) {
            Text(
                text = if (routine.steps.isEmpty() || routine.currentStepIndex >= routine.steps.size - 1) "Completar rutina 🎉" else "Siguiente paso",
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

// ================= THE TIENDA (WITH CART) =================
@Composable
fun TiendaView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll)
            .statusBarsPadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text("Tienda MyAura 🛍️", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Text(
            text = "Artículos en carrito: ${state.shoppingCart.sumOf { it.quantity }}  • Total: $${state.shoppingCart.sumOf { it.item.price * it.quantity }.toInt()} COP",
            color = AuraLavenderPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (state.shoppingCart.isNotEmpty()) {
            Button(
                onClick = { viewModel.simulatePurshase() },
                colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("Simular compra e instalar Kit ✨", color = Color.White)
            }
        }

        Text("Kits antiestrés (Físicos):", fontWeight = FontWeight.Bold)
        StaticData.kits.forEach { kit ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(kit.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(kit.description, fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$${kit.price.toInt()} COP", fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                        Button(
                            onClick = { viewModel.addToCart(kit) }, 
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Añadir", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Suscripciones de Bienestar:", fontWeight = FontWeight.Bold)
        StaticData.subscriptions.forEach { sub ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(sub.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(sub.description, fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("$${sub.price.toInt()} COP / mes", fontWeight = FontWeight.Bold)
                        Button(
                            onClick = { viewModel.addToCart(sub) }, 
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Comprar", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Repuestos y Accesorios (Refills):", fontWeight = FontWeight.Bold)
        StaticData.refills.forEach { refill ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(refill.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(refill.description, fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$${refill.price.toInt()} COP", fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                        Button(
                            onClick = { viewModel.addToCart(refill) }, 
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Añadir", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ================= DETAILED SUB-PAGES EXPLORAR =================

@Composable
fun WorkshopDetailScreen(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()
    val activeWorkshop = state.activeWorkshop

    // Local state to track which module is currently selected for interactive detail view
    var activeModule by remember { mutableStateOf<WorkshopModule?>(null) }
    var activeReflexAnswer by remember { mutableStateOf("") }
    var showDownloadToast by remember { mutableStateOf("") }

    if (activeWorkshop != null) {
        // If an active module is open, show the detail screen of that MODULE
        if (activeModule != null) {
            val mod = activeModule!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { activeModule = null }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás al taller")
                    }
                    Text("Módulo: ${mod.title}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Simulated Video Player ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Reproduciendo video práctico interactivo 🎥", color = Color.White, fontSize = 12.sp)
                        Text(mod.durationString, color = AuraMintAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Reading text ---
                Text("Lectura Corta:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Card(
                    colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, SleekSlate200),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        text = mod.readingText,
                        fontSize = 13.sp,
                        color = SleekSlate700,
                        modifier = Modifier.padding(14.dp),
                        lineHeight = 18.sp
                    )
                }

                // --- Downloadable PDF ---
                Button(
                    onClick = { showDownloadToast = "Guardando archivo ${mod.downloadablePdfName} en almacenamiento local..." },
                    colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Descargar guía PDF: ${mod.downloadablePdfName} 📄", color = Color.White, fontSize = 12.sp)
                }

                if (showDownloadToast.isNotEmpty()) {
                    Text(showDownloadToast, fontSize = 11.sp, color = AuraMintAccent, modifier = Modifier.padding(bottom = 8.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // --- Practical activity ---
                Text("Actividad Práctica Recomendada:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Card(
                    colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(mod.practicalActivity, fontSize = 13.sp, color = TextDark)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // --- Reflection Question ---
                Text("Pregunta de Reflexión:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Spacer(modifier = Modifier.height(4.dp))
                Text(mod.reflectionQuestion, fontSize = 13.sp, fontStyle = FontStyle.Italic, color = SleekSlate600)
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = activeReflexAnswer,
                    onValueChange = { activeReflexAnswer = it },
                    placeholder = { Text("Escribe tu introspección aquí...", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark,
                        focusedBorderColor = AuraLavenderPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- Action buttons for the module ---
                if (mod.isCompleted) {
                    Button(
                        onClick = { activeModule = null },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekTealMuted),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("¡Módulo Completado! Volver ✅", color = Color.White)
                    }
                } else {
                    Button(
                        onClick = {
                            viewModel.completeModule(activeWorkshop.id, mod.id)
                            activeModule = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Marcar Módulo como Completado ✔️", color = Color.White)
                    }
                }
            }
        } else {
            // General Details Screen of the selected workshop
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .statusBarsPadding()
                    .verticalScroll(scroll)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.closeActiveWorkshop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                    Text("Detalle del Taller", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(activeWorkshop.title, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = AuraLavenderPrimary)
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SleekSlate100),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Duración: 45 total mins",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate800,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SleekSlate100),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Modalidad: Mixto",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SleekSlate800,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Objetivos y de más ---
                Text("Objetivo del Taller:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Text(activeWorkshop.objective, fontSize = 13.sp, color = SleekSlate600, modifier = Modifier.padding(vertical = 4.dp))

                Spacer(modifier = Modifier.height(10.dp))

                Text("Descripción Ampliada:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Text(activeWorkshop.description, fontSize = 13.sp, color = SleekSlate600, modifier = Modifier.padding(vertical = 4.dp))

                Spacer(modifier = Modifier.height(10.dp))

                Text("Qué aprenderás:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Text("• Manejo corporal de los picos de estrés mediante posturas de liberación.\n• Técnicas de respiración rítmica aplicadas a jornadas largas de alta carga mental.\n• Ejercicios introspección guiada para identificar gatillos emocionales habituales.", fontSize = 13.sp, color = SleekSlate600, modifier = Modifier.padding(vertical = 4.dp))

                Spacer(modifier = Modifier.height(10.dp))

                Text("Para quién es recomendado:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Text("Personas con rutinas exigentes de estudio o trabajo, propensión a la fatiga emocional nocturna, o quienes buscan equilibrio integral diario.", fontSize = 13.sp, color = SleekSlate600, modifier = Modifier.padding(vertical = 4.dp))

                Spacer(modifier = Modifier.height(12.dp))

                // --- Precios ---
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, SleekSlate200),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Inversión del Taller:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Precio Público: $${activeWorkshop.publicPrice.toInt()} COP/único", fontSize = 12.sp, color = SleekSlate600)
                            Text("Precio Premium: Incluido con Kit RITUAL ✨", fontSize = 12.sp, color = AuraLavenderPrimary, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- Progress Bar ---
                val completedCount = activeWorkshop.modules.count { it.isCompleted }
                val totalModules = activeWorkshop.modules.size
                val progressPercent = if (totalModules > 0) completedCount.toFloat() / totalModules.toFloat() else 0f
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Progreso del taller: $completedCount de $totalModules módulos completados (${(progressPercent * 100).toInt()}%)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = AuraLavenderPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progressPercent },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                        color = AuraLavenderPrimary,
                        trackColor = SleekSlate100
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- Workshop action buttons ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            activeModule = activeWorkshop.modules.firstOrNull()
                        },
                        modifier = Modifier.weight(1f).height(44.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Empezar taller 🚀", fontSize = 12.sp, color = Color.White)
                    }

                    Button(
                        onClick = {
                            val nextIncomplete = activeWorkshop.modules.find { !it.isCompleted } ?: activeWorkshop.modules.firstOrNull()
                            activeModule = nextIncomplete
                        },
                        modifier = Modifier.weight(1f).height(44.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Continuar taller ➡️", fontSize = 12.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val isSaved = activeWorkshop.isLibrarySaved
                Button(
                    onClick = { viewModel.toggleWorkshopLibrary(activeWorkshop.id) },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSaved) SleekTealMuted else SleekSlate200),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    val label = if (isSaved) "Taller guardado en biblioteca ⭐" else "Guardar taller en biblioteca 📂"
                    Text(label, fontSize = 12.sp, color = if (isSaved) Color.White else TextDark, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- Modules list ---
                Text("Módulos incluidos en el taller:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SleekSlate800)
                Spacer(modifier = Modifier.height(8.dp))

                activeWorkshop.modules.forEach { mod ->
                    Card(
                        onClick = { activeModule = mod },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, SleekSlate200),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(mod.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Duración: ${mod.durationString}", fontSize = 12.sp, color = TextSecondary)
                            }
                            if (mod.isCompleted) {
                                Text("COMPLETADO ✅", fontSize = 11.sp, color = SleekTealMuted, fontWeight = FontWeight.Bold)
                            } else {
                                Text("PENDIENTE ⏳", fontSize = 11.sp, color = AuraLavenderPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Fallback or Main Workshops listing page
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scroll)
                .statusBarsPadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
                Text("Talleres Formativos 📚", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Text("Aprende a respirar, regular emociones y reaccionar con pausas de bienestar.", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(bottom = 12.dp))

            state.workshopsList.forEach { w ->
                Card(
                    onClick = { viewModel.openWorkshop(w) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, SleekSlate200)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(w.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AuraLavenderPrimary)
                        Text(w.description, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Precio Público: $${w.publicPrice.toInt()} COP • Premium: Gratuito ✨", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SleekSlate800)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Ver taller e iniciar sus módulos ➡️", fontSize = 12.sp, color = AuraLavenderPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun MasterclassesView(state: MyAuraState, viewModel: MyAuraViewModel) {
    var activePlayingMasterclass by remember { mutableStateOf<Masterclass?>(null) }
    var isPlayingVideo by remember { mutableStateOf(true) }
    var videoProgress by remember { mutableStateOf(0.42f) }

    if (activePlayingMasterclass != null) {
        val mc = activePlayingMasterclass!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { activePlayingMasterclass = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
                Text("Reproductor Aura Play 🎬", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Simulated premium video interface
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Background video mock graphic
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(SleekSlate900, AuraLavenderPrimary.copy(alpha = 0.4f))
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "AURA PREMIUM STREAMING 🔮",
                            fontSize = 11.sp,
                            color = AuraMintAccent,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = mc.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Por ${mc.facilitator}",
                            fontSize = 11.sp,
                            color = Color.White.copy(0.7f)
                        )
                    }

                    // Progress bar
                    LinearProgressIndicator(
                        progress = { videoProgress },
                        color = AuraLavenderPrimary,
                        trackColor = Color.White.copy(0.3f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .align(Alignment.BottomCenter)
                    )

                    // Control Overlay Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { isPlayingVideo = !isPlayingVideo }) {
                            Icon(
                                imageVector = if (isPlayingVideo) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "18:24 / ${mc.durationMinutes}:00 min",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Masterclass Objective Header as user requested
            Text(
                "Objetivo de Aprendizaje:",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = SleekSlate800
            )
            Text(
                text = "Desbloquear herramientas prácticas para calmar el córtex prefrontal en menos de 10 minutos guiado por ${mc.facilitator}. Esta sesión te permite alinear tu respiración y usar fragancias relajantes para recuperar el autocontrol inmediato bajo tensión intensa.",
                fontSize = 13.sp,
                color = TextSecondary,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Premium Low Cost Pricing Reminder
            Card(
                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        "💰 Democratización del Bienestar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = AuraLavenderPrimary
                    )
                    Text(
                        text = "Esta sesión exclusiva tiene un precio accesible de solo $${mc.price.toInt()} COP (o incluido en tu Plan Premium de bajo costo) para hacer accesible el autocuidado sin barreras.",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Masterclass Resources list
            Text(
                "Material de Apoyo Incluido:",
                fontWeight = FontWeight.Bold,
                color = SleekSlate800,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AuraMintAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guía práctica de ejercicios (PDF)", fontSize = 12.sp, color = TextDark)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AuraMintAccent, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Plantilla de Habit Tracker de 2 minutos", fontSize = 12.sp, color = TextDark)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { activePlayingMasterclass = null },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Marcar de Clase como Completada ✓", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    } else {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scroll)
                .statusBarsPadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
                Text("Masterclasses exclusivas 🎬", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Objective Card describing Masterclasses
            Card(
                colors = CardDefaults.cardColors(containerColor = AuraLavenderBase),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, AuraLavenderPrimary.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🎯 Propósito de las Masterclasses",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = AuraLavenderPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Clases exclusivas de bajo costo impartidas por profesionales líderes en salud mental, psicología y terapias físicas. Nuestro fin es democratizar el conocimiento de primer nivel haciéndolo accesible para todos.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            state.masterclassesList.forEach { mc ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, SleekSlate200),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(mc.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                        Text("Impartido por: ${mc.facilitator}", fontSize = 12.sp, color = AuraLavenderPrimary)
                        Text("Duración: ${mc.durationMinutes} minutos", fontSize = 11.sp, color = TextSecondary)
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Bajo costo: $${mc.price.toInt()} COP",
                                fontWeight = FontWeight.SemiBold,
                                color = AuraMintAccent,
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Button(
                            onClick = { activePlayingMasterclass = mc },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                            modifier = Modifier.fillMaxWidth().height(38.dp)
                        ) {
                            Text("Ver Clase & Ejemplo de Interfaz 🎬", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventsView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll)
            .statusBarsPadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text("Eventos en Medellín 📍", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Explanation Header
        Text(
            text = "Encuentros presenciales diseñados para profundizar en tu bienestar y conectar con la comunidad.",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        StaticData.physicalEvents.forEach { ev ->
            // Enriched event details based on ID
            val (objective, itinerary, instructions) = when (ev.id) {
                "pe1" -> Triple(
                    "Tonificar y estirar el cuerpo con movimientos fluidos sincronizados con aceites de lavanda para liberar el cortisol acumulado de la semana.",
                    "10:00 AM - Bienvenida aromática y respiración guiada\n10:15 AM - Sesión de Pilates consciente\n11:15 AM - Hidratación, matcha helado y socialización",
                    "Traer ropa cómoda deportiva, tapete de yoga (opcional) y botella para recarga de agua."
                )
                "pe2" -> Triple(
                    "Lograr una reconexión biológica con la naturaleza a través de yoga asana suave, reduciendo la ansiedad mediante el contacto directo con la tierra.",
                    "8:30 AM - Punto de encuentro entrada teleférico\n8:45 AM - Caminata descalza y estiramientos suaves\n9:30 AM - Vinyasa Flow rodeado de cipreses",
                    "Ropa para clima templado, protector solar amable y muchas ganas de respirar fresco."
                )
                "pe3" -> Triple(
                    "Calmar y silenciar las ondas cerebrales a través del baño sonoro, utilizando la acústica majestuosa del atardecer para meditar profundamente.",
                    "5:30 PM - Posicionamiento frente al mirador panorámico\n5:45 PM - Sintonización de cuencos acústicos\n6:30 PM - Infusión caliente de hierbas relajantes",
                    "Traer abrigo grueso (hace frío al anochecer), colchoneta cómoda y un antifaz."
                )
                "pe4" -> Triple(
                    "Activar la salud cardiovascular mediante una caminata moderada y recargar nutrientes con un brunch orgánico curado por nutricionistas.",
                    "7:00 AM - Calentamiento inicial en el ingreso de la reserva\n7:15 AM - Trekking por sendero de la cascada\n9:30 AM - Brunch social consciente",
                    "Calzado de buen agarre, gorra, repelente natural y termo MyAura para mantenerte hidratado."
                )
                else -> Triple(
                    "Aprender el arte milenario del batido de té matcha y explorar metodologías de regulación emocional estructuradas para la vida contemporánea.",
                    "4:00 PM - Demostración de purificación de utensilios\n4:20 PM - Práctica individual de batido manual ceremonial\n4:50 PM - Círculo de palabra sobre límites saludables",
                    "El taller incluye el kit básico de matcha de obsequio. No requiere experiencia previa."
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(ev.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Place, contentDescription = null, tint = AuraLavenderPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(ev.place, fontSize = 11.sp, color = AuraLavenderPrimary, fontWeight = FontWeight.SemiBold)
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(ev.date, fontSize = 11.sp, color = TextSecondary)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Objective details
                    Text("🎯 Objetivo del encuentro:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(objective, fontSize = 11.sp, color = TextSecondary)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Location details
                    Text("📍 Itinerario detallado:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(itinerary, fontSize = 11.sp, color = TextSecondary)

                    Spacer(modifier = Modifier.height(8.dp))

                    // Instructions
                    Text("📝 Recomendaciones clave:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(instructions, fontSize = 11.sp, color = TextSecondary)

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Text("Reservar Cupo ($${ev.price.toInt()} COP)", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ActionsView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scrollState = rememberScrollState()
    var isScanning by remember { mutableStateOf(false) }
    var showScanSuccessAlert by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text("Tus Invitaciones y Activaciones 🌟", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Brief Description requested by user
        Card(
            colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "✉️ Información Importante:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AuraLavenderPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Aquí podrás encontrar todas las invitaciones que tienes con los detalles de la invitación con fecha, objetivo, lugar y para que confirmes tu asistencia.",
                    fontSize = 13.sp,
                    color = TextDark,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trigger QR Scanner Simulation
        if (!isScanning) {
            Button(
                onClick = {
                    isScanning = true
                    showScanSuccessAlert = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📷 Escanear QR de Invitación", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        } else {
            // Simulated Cameraview Scanning animation
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Simulando cámara del celular...", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("[ 🔲 Centra el código QR de tu invitación ]", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Action button to simulate scan event completed
                        Button(
                            onClick = {
                                viewModel.addInvitationFromQR()
                                isScanning = false
                                showScanSuccessAlert = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("¡Simular Detección de QR! ⚡", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        if (showScanSuccessAlert) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🎉 ¡Invitación escaneada con éxito! Revisa los detalles abajo y confirma.", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "📬 Lista de Invitaciones Recibidas",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (state.invitations.isEmpty()) {
            Text("No tienes invitaciones registradas. Escanea un código QR arriba para registrar la tuya.", fontSize = 12.sp, color = TextSecondary)
        } else {
            state.invitations.forEach { invitation ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, if (invitation.isConfirmed) AuraMintAccent.copy(0.4f) else SleekSlate200),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(invitation.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f))
                            if (invitation.isConfirmed) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("CONFIRMADO ✓", fontSize = 10.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            } else {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("PENDIENTE ⏳", fontSize = 10.sp, color = Color(0xFFE65100), fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Details list
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = AuraLavenderPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Fecha: ${invitation.date}", fontSize = 12.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Place, contentDescription = null, tint = AuraLavenderPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Lugar: ${invitation.place}", fontSize = 12.sp, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Objective (Objetivo)
                        Text("🎯 Objetivo de este encuentro:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(invitation.objective, fontSize = 12.sp, color = TextSecondary, lineHeight = 16.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        if (!invitation.isConfirmed) {
                            Button(
                                onClick = { viewModel.confirmInvitation(invitation.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(38.dp)
                            ) {
                                Text("Confirmar Asistencia ✍️", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("¡Asistencia confirmada! Nos vemos allí.", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()
    var selectedMonth by remember { mutableStateOf("Mayo 2026") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
                Text("Mi Calendario 📅", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }
            
            // Month toggle button
            TextButton(
                onClick = { selectedMonth = if (selectedMonth == "Mayo 2026") "Junio 2026" else "Mayo 2026" },
                modifier = Modifier.background(AuraLavenderBase, RoundedCornerShape(12.dp))
            ) {
                Text(selectedMonth, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Revisa las fechas clave de tus encuentros reservados y masterclasses en un solo lugar.",
            fontSize = 12.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Sleek Calendar Grid Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Days of week
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("L", "M", "M", "J", "V", "S", "D").forEach { day ->
                        Text(day, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SleekSlate500, textAlign = TextAlign.Center, modifier = Modifier.width(32.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Mayo 2026 Grid
                if (selectedMonth == "Mayo 2026") {
                    // 5 rows of Mayo
                    val weeks = listOf(
                        listOf("", "", "", "", "1", "2", "3"),
                        listOf("4", "5", "6", "7", "8", "9", "10"),
                        listOf("11", "12", "13", "14", "15", "16", "17"),
                        listOf("18", "19", "20", "21", "22", "23", "24"),
                        listOf("25", "26", "27", "28", "29", "30", "31")
                    )

                    weeks.forEach { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            week.forEach { dayText ->
                                val hasEvent = dayText == "12" || dayText == "18" || dayText == "26" || dayText == "30"
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                dayText == "12" -> AuraLavenderPrimary.copy(0.2f)
                                                dayText == "18" -> AuraMintAccent.copy(0.2f)
                                                dayText == "26" -> SleekBlueMuted.copy(0.15f)
                                                dayText == "30" -> AuraLavenderPrimary.copy(0.15f)
                                                else -> Color.Transparent
                                            }
                                        )
                                        .border(
                                            width = if (hasEvent) 1.5.dp else 0.dp,
                                            color = when {
                                                dayText == "12" -> AuraLavenderPrimary
                                                dayText == "18" -> AuraMintAccent
                                                dayText == "26" -> AuraBlueAccent
                                                dayText == "30" -> AuraLavenderPrimary
                                                else -> Color.Transparent
                                            },
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dayText,
                                        fontSize = 11.sp,
                                        fontWeight = if (hasEvent) FontWeight.ExtraBold else FontWeight.Normal,
                                        color = if (hasEvent) TextDark else SleekSlate600
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Junio 2026 Grid
                    val weeks = listOf(
                        listOf("1", "2", "3", "4", "5", "6", "7"),
                        listOf("8", "9", "10", "11", "12", "13", "14"),
                        listOf("15", "16", "17", "18", "19", "20", "21"),
                        listOf("22", "23", "24", "25", "26", "27", "28"),
                        listOf("29", "30", "", "", "", "", "")
                    )

                    weeks.forEach { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            week.forEach { dayText ->
                                val hasEvent = dayText == "4" || dayText == "15"
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (hasEvent) AuraMintAccent.copy(0.2f) else Color.Transparent)
                                        .border(
                                            width = if (hasEvent) 1.5.dp else 0.dp,
                                            color = if (hasEvent) AuraMintAccent else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dayText,
                                        fontSize = 11.sp,
                                        fontWeight = if (hasEvent) FontWeight.ExtraBold else FontWeight.Normal,
                                        color = if (hasEvent) TextDark else SleekSlate600
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Redirection Cards Section
        Text(
            "Tus Citas de Bienestar Agendadas:",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = SleekSlate800,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (selectedMonth == "Mayo 2026") {
            // Event 1
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🧘 Pilates & Pausa MyAura", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("12 MAY - 10 AM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                    }
                    Text("Clase presencial de estiramiento y aromaterapia en El Poblado.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.EventsList) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Ir a detalles del evento 📍", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            // Event 2
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🍀 Yoga para soltar el estrés", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("18 MAY - 8:30 AM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                    }
                    Text("Yoga suave al aire libre rodeados de árboles en el Parque Arví, Medellín.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.EventsList) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Ir a detalles del evento 📍", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            // Event 3 (Masterclass)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎬 Autocuidado: Micro-pausas de 2 min", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("26 MAY - Bajo Costo", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraBlueAccent)
                    }
                    Text("Masterclass interactiva para aprender a respirar en tus descansos con Ana María Vélez.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.MasterclassesList) },
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Iniciar Masterclass en Video 🎬", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            // Event 4
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔮 Meditación al atardecer", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("30 MAY - 5:30 PM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                    }
                    Text("Práctica zen con cuencos tibetanos contemplando las hermosas montañas en San Félix.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.EventsList) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Ir a detalles del evento 📍", color = Color.White, fontSize = 11.sp)
                    }
                }
            }
        } else {
            // Junio Events
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🥾 Caminata consciente y brunch", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("04 JUN - 7:00 AM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                    }
                    Text("Senderismo de desconexión y alimentación saludable en Envigado.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.EventsList) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Ir a detalles del evento 📍", color = Color.White, fontSize = 11.sp)
                    }
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, SleekSlate200),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🍵 Matcha, café & charla consciente", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                        Text("15 JUN - 4:00 PM", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                    }
                    Text("Taller interactivo de matcha ceremonial y límites espirituales en Laureles.", fontSize = 11.sp, color = TextSecondary, modifier = Modifier.padding(vertical = 4.dp))
                    
                    Button(
                        onClick = { viewModel.updateScreen(Screen.EventsList) },
                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(36.dp)
                    ) {
                        Text("Ir a detalles del evento 📍", color = Color.White, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun CorporateView(state: MyAuraState, viewModel: MyAuraViewModel) {
    if (state.corporateRole == null) {
        CorporateRoleSelectionView(viewModel)
    } else if (state.corporateRole == "host") {
        CorporateHostDashboardView(state, viewModel)
    } else {
        CorporateGuestDashboardView(state, viewModel)
    }
}

@Composable
fun CorporateRoleSelectionView(viewModel: MyAuraViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FC))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.updateScreen(Screen.Main) },
                modifier = Modifier.background(Color.White, CircleShape).border(1.dp, SleekSlate100, CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = SleekSlate700)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text("Modo Corporativo 🏢", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
        }

        Spacer(modifier = Modifier.height(36.dp))

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(AuraLavenderBase)
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("💼", fontSize = 36.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Cómo quieres ingresar?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = SleekSlate900,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Bienestar mental, antiestrés y pausas saludables para tu organización o comunidad privada.",
            fontSize = 13.sp,
            color = SleekSlate500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 32.dp)
        )

        // Option 1: Host
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { viewModel.selectCorporateRole("host") }
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SleekLavenderBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountBox, contentDescription = "Anfitrión", tint = SleekLavenderMuted, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Soy anfitrión", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
                    Text("Administra tu empresa, universidad o comunidad, ve analíticas agregadas y asigna retos.", fontSize = 12.sp, color = SleekSlate500)
                }
                Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente", tint = SleekSlate400)
            }
        }

        // Option 2: Guest
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.selectCorporateRole("guest") }
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SleekTealBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Invitado", tint = AuraMintAccent, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Soy usuario invitado", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
                    Text("Ingresa a la comunidad de tu organización, realiza tus rutinas asignadas y únete a retos.", fontSize = 12.sp, color = SleekSlate500)
                }
                Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente", tint = SleekSlate400)
            }
        }
    }
}

@Composable
fun CorporateHostDashboardView(state: MyAuraState, viewModel: MyAuraViewModel) {
    var editingName by remember { mutableStateOf(state.corporateCommunityName) }
    var selectedOrgType by remember { mutableStateOf(state.corporateCommunityType) }
    var generatedCode by remember { mutableStateOf("") }
    var inviteEmail by remember { mutableStateOf("") }

    var routineMessage by remember { mutableStateOf<String?>(null) }
    var workshopMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FC))
            .statusBarsPadding()
    ) {
        // Upper Panel Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .border(BorderStroke(0.dp, Color.Transparent))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { viewModel.selectCorporateRole(null) },
                    modifier = Modifier.background(Color(0xFFF5F5FA), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = SleekSlate700)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("Panel Corporativo MyAura 📊", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Gestiona el bienestar de tu comunidad de forma privada, medible y humana.",
                fontSize = 12.sp,
                color = SleekSlate500
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // 1. CREAR / EDITAR COMUNIDAD
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("1. Tu Comunidad Privada 🛡️", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        OutlinedTextField(
                            value = editingName,
                            onValueChange = { 
                                editingName = it
                                viewModel.updateCorporateCommunity(selectedOrgType, it)
                            },
                            label = { Text("Nombre de Comunidad") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Tipo de Organización:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SleekSlate600)
                        
                        val orgTypes = listOf("Empresa", "Universidad", "Gimnasio", "Estudio de pilates", "Centro deportivo", "Comunidad")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            orgTypes.forEach { type ->
                                val isSelected = type == selectedOrgType
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) AuraLavenderPrimary else SleekSlate100)
                                        .clickable { 
                                            selectedOrgType = type 
                                            viewModel.updateCorporateCommunity(type, editingName)
                                        }
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = type,
                                        fontSize = 11.sp,
                                        color = if (isSelected) Color.White else SleekSlate700,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 2. REGISTRO DIARIO Y METRICAS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("2. Métricas de Participación 📈", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(14.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            MetricBox(title = "Miembros", valStr = "${state.corporateActiveMembers}/${state.corporateTotalMembers}", modifier = Modifier.weight(1f))
                            MetricBox(title = "Check-ins Sem", valStr = "${state.corporateWeeklyCheckins}", modifier = Modifier.weight(1f))
                            MetricBox(title = "Rutinas Compl", valStr = "${state.corporateWeeklyRoutines}", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // 3. AURA ANALIZA TU COMUNIDAD (Aggregation/Privacy)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(0.2f)),
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(1.dp, AuraLavenderBase.copy(0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✨", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Aura analiza tu comunidad", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = AuraLavenderPrimary)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        BulletMetricRow("Nivel de estrés promedio del grupo:", state.corporateGroupStressLevel, Color.Red)
                        BulletMetricRow("Energía promedio consolidada:", state.corporateGroupEnergyLevel, AuraLavenderPrimary)
                        BulletMetricRow("Necesidad primordial colectiva:", state.corporateGroupTopNeed, Color(0xFF4CAF50))

                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(0.7f))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Text("⚠️", fontSize = 14.sp, modifier = Modifier.padding(end = 6.dp))
                                Text(
                                    "Por respeto y privacidad, MyAura recopila respuestas de forma anónima y agregada. No tendrás acceso a las respuestas individuales ni chats personales de tus usuarios.",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SleekSlate600
                                )
                            }
                        }
                    }
                }
            }

            // 4. INVITACIONES
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("3. Invitar Miembros ✉️", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = inviteEmail,
                            onValueChange = { inviteEmail = it },
                            placeholder = { Text("correo@organizacion.com o Nombre") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                val ran = (100..999).random()
                                generatedCode = "#AURA-${ran}-INV"
                                inviteEmail = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp)
                        ) {
                            Text("Generar Enlace / Código de Acceso", color = Color.White)
                        }

                        if (generatedCode.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SleekTealBg)
                                    .border(1.dp, AuraMintAccent.copy(0.5f), RoundedCornerShape(12.dp))
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Código de Invitación: $generatedCode (Copiado a portapapeles)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Miembros activos en la comunidad (Simulación):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SleekSlate700)
                        
                        val fakeMembers = listOf("Ana María Rendón (Activa)", "Juan Camilo Castro (Activo)", "Luisa Ortega (Activa)", "Andrés Felipe Ruiz (Invitado)")
                        Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            fakeMembers.forEach { mem ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(SleekSlate50)
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(mem, fontSize = 11.sp, color = SleekSlate700)
                                    Text("Online", fontSize = 10.sp, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // 5. ASIGNAR RUTINAS SALUDABLES
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("4. Asignar Rutinas Saludables del Kit 🌸", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Text("Define qué rutinas se destacarán prioritariamente en el dashboard de tus invitados hoy.", fontSize = 11.sp, color = SleekSlate500, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

                        if (routineMessage != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SleekLavenderBg)
                                    .padding(8.dp)
                            ) {
                                Text(routineMessage!!, color = SleekLavenderMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        StaticData.independentRoutines.forEach { r ->
                            val isAssigned = state.corporateAssignedRoutines.contains(r.id)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SleekSlate50)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(r.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SleekSlate800)
                                    Text("Objetivo: ${r.objective}", fontSize = 10.sp, color = SleekSlate500)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isAssigned) AuraLavenderPrimary else SleekSlate200)
                                        .clickable { 
                                            viewModel.assignCorporateRoutine(r.id)
                                            routineMessage = "Se actualizó el estado de asignación de '${r.name}' para el grupo."
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(if (isAssigned) "Ásignada ✓" else "Asignar", fontSize = 10.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            // 6. ASIGNAR RETOS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("5. Activar Retos de Bienestar 🎯", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Reto hoy: ${state.corporateActiveChallenge}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AuraLavenderPrimary)
                        
                        Row(modifier = Modifier.padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Miembros inscriptos: ${state.corporateChallengeParticipantsCount}", fontSize = 11.sp, color = SleekSlate500)
                            Text("•", fontSize = 11.sp, color = SleekSlate400)
                            Text("Completados: ${state.corporateChallengeCompletedCount}", fontSize = 11.sp, color = SleekSlate500)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Cambiar reto activo de la comunidad:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SleekSlate700)
                        
                        val challengeOptions = listOf("Reto de respiración de 3 minutos", "Reto de 10,000 pasos de calma", "Reto diario de diario de gratitud")
                        challengeOptions.forEach { chal ->
                            val isActive = chal == state.corporateActiveChallenge
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isActive) AuraLavenderBase.copy(0.3f) else SleekSlate50)
                                    .clickable { viewModel.changeCorporateActiveChallenge(chal) }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(chal, fontSize = 11.sp, color = SleekSlate800)
                                if (isActive) {
                                    Icon(Icons.Default.Star, contentDescription = "Activo", tint = AuraLavenderPrimary, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }

            // 7. TALLERES EMPRESARIALES, CLASES Y EVENTOS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("6. Talleres Empresariales y Clases 🧘‍♀️", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SleekSlate900)
                        Text("Eleva el bienestar grupal reservando experiencias guiadas por instructores certificados.", fontSize = 11.sp, color = SleekSlate500, modifier = Modifier.padding(top = 4.dp, bottom = 12.dp))

                        if (workshopMessage != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SleekTealBg)
                                    .padding(8.dp)
                            ) {
                                Text(workshopMessage!!, color = AuraMintAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        val corporateOfferings = listOf(
                            "Taller Físico Presencial: Iniciación a la Aromaterapia" to "Inhalaciones rítmicas con aceites esenciales.",
                            "Aura Masterclass: Manejo de Estrés con Kit PAUSA" to "Especialmente diseñado para rebajar picos laborales intensos.",
                            "Sesión Sincrónica Grupal: Meditación de Mediodía" to "Reenergiza cuerpo y mente en solo 15 min."
                        )

                        corporateOfferings.forEach { offering ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(offering.first, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = SleekSlate800)
                                    Text(offering.second, fontSize = 10.sp, color = SleekSlate500, modifier = Modifier.padding(top = 2.dp, bottom = 10.dp))
                                    
                                    Button(
                                        onClick = { workshopMessage = "¡Tu reserva para '${offering.first}' ha sido confirmada con éxito! Nos comunicaremos por correo." },
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                                        modifier = Modifier.fillMaxWidth().height(34.dp)
                                    ) {
                                        Text("Reservar taller para mi equipo 📅", fontSize = 10.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun CorporateGuestDashboardView(state: MyAuraState, viewModel: MyAuraViewModel) {
    var chalMessage by remember { mutableStateOf<String?>(null) }
    var scaleRatingFloat by remember { mutableStateOf(5f) }
    val scaleRating = scaleRatingFloat.toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAF9))
            .statusBarsPadding()
    ) {
        // Guest Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { viewModel.selectCorporateRole(null) },
                    modifier = Modifier.background(Color(0xFFF5F5FA), CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = SleekSlate700)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text("Canal de Bienestar 🌿", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SleekSlate800)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Tu espacio para respirar, recargar y florecer junto a tu comunidad.",
                fontSize = 12.sp,
                color = SleekSlate500
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // 1. TU COMUNIDAD
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🏢", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Mi Comunidad Privada:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SleekSlate800)
                        }
                        Text(state.corporateCommunityName, fontSize = 18.sp, fontWeight = FontWeight.Black, color = AuraLavenderPrimary, modifier = Modifier.padding(top = 4.dp))
                        Text(
                            text = "Canal habilitado por tipo: ${state.corporateCommunityType}. Miembros sincronizados: ${state.corporateActiveMembers} participantes.",
                            fontSize = 11.sp,
                            color = SleekSlate500,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // 2. CONSEJOS E INDICACIONES GRUPALES
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AuraLavenderBase.copy(0.15f)),
                    shape = RoundedCornerShape(26.dp),
                    border = BorderStroke(1.dp, AuraLavenderBase.copy(0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Aura aconseja al grupo", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = AuraLavenderPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        val isHighStress = state.corporateGroupStressLevel.contains("Alto") || state.corporateGroupStressLevel.contains("7")
                        if (isHighStress) {
                            Text(
                                text = "El estrés consolidado de tu comunidad es actualmente Alto (${state.corporateGroupStressLevel}). Aura aconseja realizar pausas activas preventivas antes de volver a tus tareas. ¡Usa tu inhalador nasal de sienes!",
                                fontSize = 11.sp,
                                color = SleekSlate700
                            )
                        } else {
                            Text(
                                text = "El nivel estrés consolidado está bajo control. ¡Es un buen momento para una sintonización de enfoque mental de 2 minutos!",
                                fontSize = 11.sp,
                                color = SleekSlate700
                            )
                        }
                    }
                }
            }

            // 3. RUTINAS ASIGNADAS POR EL ANFITRIÓN
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("Rutinas sugeridas por el anfitrión hoy:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SleekSlate800)
                        Spacer(modifier = Modifier.height(10.dp))

                        val assigned = StaticData.independentRoutines.filter { state.corporateAssignedRoutines.contains(it.id) }
                        if (assigned.isEmpty()) {
                            Text("Tu anfitrión no ha asignado rutinas específicas hoy. Elige del menú de sintonización general.", fontSize = 11.sp, color = SleekSlate500)
                        } else {
                            assigned.forEach { r ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(r.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SleekSlate800)
                                            Text("Objetivo: ${r.objective}", fontSize = 10.sp, color = SleekSlate500)
                                        }
                                        Button(
                                            onClick = { viewModel.selectRoutine(r) },
                                            colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.height(34.dp)
                                        ) {
                                            Text("Play ▶", fontSize = 10.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. RETOS DE LA COMUNIDAD (Unirse)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("Retos Activos de la Comunidad 🎯", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(state.corporateActiveChallenge, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                        
                        Row(modifier = Modifier.padding(vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("Participantes: ${state.corporateChallengeParticipantsCount}", fontSize = 11.sp, color = SleekSlate500)
                            Text("Completados: ${state.corporateChallengeCompletedCount}", fontSize = 11.sp, color = SleekSlate500)
                        }

                        if (chalMessage != null) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SleekTealBg)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(chalMessage!!, color = AuraMintAccent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.toggleCorporateChallengeJoined()
                                chalMessage = if (state.corporateIsJoinedChallenge) "Has abandonado el desafío actual" else "¡Te has unido con éxito al Reto de la Comunidad!"
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = if (state.corporateIsJoinedChallenge) Color.Red else AuraLavenderPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                        ) {
                            Text(if (state.corporateIsJoinedChallenge) "Dejar Reto Grupal" else "Unirme al Reto Grupal 🤝", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }

            // 5. PAUSAS COLECTIVAS SINCRONIZADAS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("Sincronización de Pausa Colectiva ⏱️", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SleekSlate900)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (state.corporateBreakAssigned) "🟢 Pausa Activa de Grupo PROGRAMADA" else "⚪ Sin pausas colectivas convocadas en este momento",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (state.corporateBreakAssigned) Color(0xFF4CAF50) else SleekSlate500
                        )
                        if (state.corporateBreakAssigned) {
                            Text(
                                "El anfitrión ha indicado un break de mediodía de 3 min. ¡Inhala aire profundamente y cierra los ojos!",
                                fontSize = 11.sp,
                                color = SleekSlate500,
                                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }

            // 6. PROGRAMA DE RESPUESTA INTERACTIVA
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.dp, SleekSlate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("¿Cómo valoras el bienestar corporativo de hoy?", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SleekSlate800)
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Muy estresante", fontSize = 10.sp, color = SleekSlate400)
                            Text("$scaleRating / 10", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AuraLavenderPrimary)
                            Text("Excelente", fontSize = 10.sp, color = SleekSlate400)
                        }
                        Slider(
                            value = scaleRatingFloat,
                            onValueChange = { scaleRatingFloat = it },
                            valueRange = 1f..10f,
                            steps = 8
                        )
                        Text(
                            "Tu respuesta es anónima y sirve para el promedio diario.",
                            fontSize = 10.sp,
                            color = SleekSlate400,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun MetricBox(title: String, valStr: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SleekSlate50),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, color = SleekSlate500, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(valStr, fontSize = 15.sp, fontWeight = FontWeight.Black, color = SleekSlate900)
        }
    }
}

@Composable
fun BulletMetricRow(title: String, value: String, colorVal: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(AuraLavenderPrimary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontSize = 11.sp, color = SleekSlate700, modifier = Modifier.weight(1.3f))
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = colorVal)
    }
}

@Composable
fun ProgressView(state: MyAuraState, viewModel: MyAuraViewModel) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll)
            .statusBarsPadding()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.updateScreen(Screen.Main) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
            Text("Mi Progreso y Logros", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Pausas completadas esta semana", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(progress = { 0.75f }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = AuraMintAccent)
                Spacer(modifier = Modifier.height(4.dp))
                Text("6 de 8 pausas recomendadas", fontSize = 12.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("🔮 Historial de Calma con Aura IA", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Aquí se guardan las decisiones del chat de Aura IA sobre tu bienestar mental.", fontSize = 11.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(12.dp))

                if (state.auraChatHistoryDecisions.isEmpty()) {
                    Text("No hay registros guardados aún. Chatea con Aura para que guarde tus decisiones de bienestar.", fontSize = 12.sp, color = TextSecondary, fontStyle = FontStyle.Italic)
                } else {
                    state.auraChatHistoryDecisions.forEach { entry ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Estatus: ${entry.decision}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AuraMintAccent)
                                    Text(entry.dateString, fontSize = 10.sp, color = TextSecondary)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Mensaje analizado: \"${entry.textSummary}\"", fontSize = 11.sp, color = TextSecondary, fontStyle = FontStyle.Italic)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineCustomizerDialog(
    state: MyAuraState,
    viewModel: MyAuraViewModel,
    onDismiss: () -> Unit
) {
    var editedAgendaList by remember { mutableStateOf(state.todayTodoTasks) }
    var expandedRoutineId by remember { mutableStateOf<String?>(null) }
    var activeEditingRoutineId by remember { mutableStateOf<String?>(null) }
    var tempRoutineStepsText by remember { mutableStateOf("") }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, SleekSlate200),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Personalizar Rutinas MyAura 🧘", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark)
                            Text("Modifica tus horarios y pasos", fontSize = 11.sp, color = TextSecondary)
                        }
                        IconButton(onClick = onDismiss, modifier = Modifier.background(SleekSlate100, CircleShape)) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = SleekSlate700)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            "1. Mi Agenda y Horarios del Día 📅",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraLavenderPrimary,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                        
                        editedAgendaList.forEachIndexed { idx, item ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                OutlinedTextField(
                                    value = item,
                                    onValueChange = { newValue ->
                                        val nextList = editedAgendaList.toMutableList()
                                        nextList[idx] = newValue
                                        editedAgendaList = nextList
                                    },
                                    singleLine = true,
                                    label = { Text("Bloque horario ${idx + 1}", fontSize = 9.sp) },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = AuraLavenderPrimary,
                                        unfocusedBorderColor = SleekSlate200
                                    )
                                )
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.updateAgendaTasks(editedAgendaList)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SleekSlate900),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .height(38.dp)
                        ) {
                            Text("Guardar Cambios de Agenda ✓", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "2. Rutinas Recomendadas/Sugeridas 🌸",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraLavenderPrimary,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        val suggestedRoutines = state.routinesList.filter { it.durationMinutes <= 5 }
                        suggestedRoutines.forEach { routine ->
                            val isExpanded = expandedRoutineId == routine.id
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                                border = BorderStroke(1.dp, SleekSlate100),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        expandedRoutineId = if (isExpanded) null else routine.id
                                        activeEditingRoutineId = null
                                    }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(routine.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                                            Text("Sugerida • ${routine.durationMinutes} min", fontSize = 10.sp, color = TextSecondary)
                                        }
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = SleekSlate600
                                        )
                                    }

                                    if (isExpanded) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Pasos de la rutina:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                                        
                                        routine.steps.forEachIndexed { _, stepStr ->
                                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                                Text("•", fontSize = 13.sp, color = AuraMintAccent, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(stepStr, fontSize = 11.sp, color = TextDark)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (activeEditingRoutineId == routine.id) {
                                            OutlinedTextField(
                                                value = tempRoutineStepsText,
                                                onValueChange = { tempRoutineStepsText = it },
                                                placeholder = { Text("Escribe cada paso en una línea nueva...") },
                                                label = { Text("Editar Pasos (Línea por línea)", fontSize = 9.sp) },
                                                modifier = Modifier.fillMaxWidth(),
                                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = AuraLavenderPrimary,
                                                    unfocusedBorderColor = SleekSlate200
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                                Button(
                                                    onClick = {
                                                        val nextSteps = tempRoutineStepsText.lines().filter { it.isNotBlank() }
                                                        viewModel.updateRoutineSteps(routine.id, nextSteps)
                                                        activeEditingRoutineId = null
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent),
                                                    modifier = Modifier.height(32.dp).weight(1f)
                                                ) {
                                                    Text("Guardar", color = Color.White, fontSize = 10.sp)
                                                }
                                                TextButton(
                                                    onClick = { activeEditingRoutineId = null },
                                                    modifier = Modifier.height(32.dp)
                                                ) {
                                                    Text("Cancelar", fontSize = 10.sp, color = Color.Red)
                                                }
                                            }
                                        } else {
                                            Button(
                                                onClick = {
                                                    activeEditingRoutineId = routine.id
                                                    tempRoutineStepsText = routine.steps.joinToString("\n")
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = SleekSlate100),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth().height(30.dp)
                                            ) {
                                                Icon(Icons.Default.Edit, contentDescription = null, tint = SleekSlate700, modifier = Modifier.size(12.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Editar Pasos", color = SleekSlate700, fontSize = 9.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            "3. Rutinas Predeterminadas de MyAura 🕯️",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AuraLavenderPrimary,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        val predefinedRoutines = state.routinesList.filter { it.durationMinutes > 5 }
                        predefinedRoutines.forEach { routine ->
                            val isExpanded = expandedRoutineId == routine.id
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SleekSlate50),
                                border = BorderStroke(1.dp, SleekSlate100),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        expandedRoutineId = if (isExpanded) null else routine.id
                                        activeEditingRoutineId = null
                                    }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(routine.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark)
                                            Text("Predeterminada • ${routine.durationMinutes} min", fontSize = 10.sp, color = TextSecondary)
                                        }
                                        Icon(
                                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = SleekSlate600
                                        )
                                    }

                                    if (isExpanded) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Pasos de la rutina:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                                        
                                        routine.steps.forEach { stepStr ->
                                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                                Text("•", fontSize = 13.sp, color = AuraMintAccent, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(stepStr, fontSize = 11.sp, color = TextDark)
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (activeEditingRoutineId == routine.id) {
                                            OutlinedTextField(
                                                value = tempRoutineStepsText,
                                                onValueChange = { tempRoutineStepsText = it },
                                                placeholder = { Text("Escribe cada paso en una línea nueva...") },
                                                label = { Text("Editar Pasos (Línea por línea)", fontSize = 9.sp) },
                                                modifier = Modifier.fillMaxWidth(),
                                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 11.sp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedBorderColor = AuraLavenderPrimary,
                                                    unfocusedBorderColor = SleekSlate200
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                                Button(
                                                    onClick = {
                                                        val nextSteps = tempRoutineStepsText.lines().filter { it.isNotBlank() }
                                                        viewModel.updateRoutineSteps(routine.id, nextSteps)
                                                        activeEditingRoutineId = null
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = AuraMintAccent),
                                                    modifier = Modifier.height(32.dp).weight(1f)
                                                ) {
                                                    Text("Guardar", color = Color.White, fontSize = 10.sp)
                                                }
                                                TextButton(
                                                    onClick = { activeEditingRoutineId = null },
                                                    modifier = Modifier.height(32.dp)
                                                ) {
                                                    Text("Cancelar", fontSize = 10.sp, color = Color.Red)
                                                }
                                            }
                                        } else {
                                            Button(
                                                onClick = {
                                                    activeEditingRoutineId = routine.id
                                                    tempRoutineStepsText = routine.steps.joinToString("\n")
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = SleekSlate100),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.fillMaxWidth().height(30.dp)
                                            ) {
                                                Icon(Icons.Default.Edit, contentDescription = null, tint = SleekSlate700, modifier = Modifier.size(12.dp))
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("Editar Pasos", color = SleekSlate700, fontSize = 9.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AuraLavenderPrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Cerrar y Volver", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
    }
}
