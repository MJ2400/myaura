package com.example

import java.io.Serializable

data class Routine(
    val id: String,
    val name: String,
    val recommendedByAura: String,
    val emotionalState: String,
    val objective: String,
    val durationMinutes: Int,
    val kitElementsUsed: List<String>,
    val noKitRequired: Boolean,
    val steps: List<String>,
    var isCompleted: Boolean = false,
    var currentStepIndex: Int = 0
) : Serializable

data class Workshop(
    val id: String,
    val title: String,
    val description: String,
    val objective: String,
    val publicPrice: Double,
    val premiumPrice: Double,
    var isPurchased: Boolean = false,
    val modules: List<WorkshopModule>,
    var isLibrarySaved: Boolean = false
)

data class WorkshopModule(
    val id: String,
    val title: String,
    val durationString: String,
    val videoUrl: String,
    val readingText: String,
    val downloadablePdfName: String,
    val practicalActivity: String,
    val reflectionQuestion: String,
    var isCompleted: Boolean = false
)

data class Masterclass(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val facilitator: String,
    val isRecorded: Boolean,
    val price: Double,
    val premiumPrice: Double,
    val simulatedVideoUrl: String,
    val resources: String,
    var isLibrarySaved: Boolean = false,
    var isPurchased: Boolean = false
)

data class PhysicalEvent(
    val id: String,
    val title: String,
    val description: String,
    val place: String,
    val date: String,
    val spots: Int,
    val price: Double,
    val premiumPrice: Double,
    var isReserved: Boolean = false
)

data class Activation(
    val id: String,
    val title: String,
    val date: String,
    val place: String,
    val spots: Int,
    val agenda: List<String>,
    val whatToBring: String,
    var isRegistered: Boolean = false
)

data class SmartwatchData(
    val steps: Int,
    val currentStressLevel: Int,
    val hrCurrent: Int,
    val hrAverage: Int,
    val activeMinutes: Int,
    val sleepString: String,
    val oxygenLevel: Int,
    val hrvValue: Int,
    val recoveryScore: Int
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val iconName: String
)

data class ShopItem(
    val id: String,
    val name: String,
    val category: String, // "Kit", "Refill", "Subscription"
    val description: String,
    val price: Double,
    val imageDescription: String,
    val compatibleKit: String = ""
)

object StaticData {
    val kits = listOf(
        ShopItem("kit_pausa", "Kit PAUSA", "Kit", "Inhalador nasal, Roll-on, Tarjetas de respiración, Objeto anti-estrés, Journal pequeño, Lapicero personalizado, Plantilla tracking de hábitos, Bolsa aromática, Caja kraft.", 84900.0, "Kit PAUSA aromaterapia y relajación"),
        ShopItem("kit_balance", "Kit BALANCE", "Kit", "Incluye todo lo de PAUSA más Vela aromática de soya, Spray relajante, Aceite esencial blend, Objeto anti-estrés premium, Journal mejorado.", 139900.0, "Kit BALANCE armonía de sentidos"),
        ShopItem("kit_ritual", "Kit RITUAL", "Kit", "Incluye todo lo de BALANCE más Difusor USB, Aceite esencial extra premium, Vela grande doble mecha, Masajeador de cabeza, Antifaz de seda, Termo premium.", 219900.0, "Kit RITUAL meditación profunda de lujo")
    )

    val subscriptions = listOf(
        ShopItem("sub_basico", "Plan Básico", "Subscription", "Rutinas básicas, Meditaciones y Check-in limitado.", 12900.0, "Suscripción Básica"),
        ShopItem("sub_plus", "Plan Plus", "Subscription", "Rutinas completas, Retos exclusivos, Comunidad y más seguimiento.", 19900.0, "Suscripción Plus"),
        ShopItem("sub_premium", "Plan Premium", "Subscription", "IA inteligente Aura, Chat ilimitado, Sincronización inteligente de reloj, 70% de descuento en talleres y mentoría.", 69900.0, "Suscripción Premium completa")
    )

    val refills = listOf(
        ShopItem("ref_inhalador", "Inhalador Nasal", "Refill", "Repuesto de inhalador de aceites esenciales puros.", 20000.0, "Inhalador esencial", "Kit PAUSA, BALANCE, RITUAL"),
        ShopItem("ref_rollon", "Roll-on Relajante", "Refill", "Repuesto de roll-on herbal 10ml contra tensiones.", 25000.0, "Rollon de sienes", "Kit PAUSA, BALANCE, RITUAL"),
        ShopItem("ref_vela", "Vela de Soya Saborizada", "Refill", "Vela artesanal relajante de cera de soya.", 35000.0, "Vela aromática", "Kit BALANCE, RITUAL"),
        ShopItem("ref_aceite", "Aceite Esencial Blend", "Refill", "Mezcla pura de lavanda, bergamota y cedro 15ml.", 45000.0, "Aceite esencial", "Kit BALANCE, RITUAL"),
        ShopItem("ref_masajeador", "Masajeador de Cabeza", "Refill", "Masajeador metálico capilar anti-tensión muscular.", 30000.0, "Masajeador capilar", "Kit RITUAL"),
        ShopItem("ref_antifaz", "Antifaz de Seda Premium", "Refill", "Antifaz suave protector de luz nocturna.", 40000.0, "Antifaz de descanso", "Kit RITUAL"),
        ShopItem("ref_termo", "Termo MyAura 350ml", "Refill", "Termo de acero inoxidable grabado.", 55000.0, "Termo grabado", "Kit RITUAL")
    )

    val independentRoutines = listOf(
        Routine("r1", "Pausa sensorial rápida", "Recomendada por Aura si tienes tensión acumulada.", "Estrés", "Bajar tensión inmediata.", 2, listOf("Inhalador nasal de aromaterapia", "Objeto anti-estrés"), false, listOf("Inhala suavemente tres veces.", "Aprieta el objeto anti-estrés.", "Suelta hombros y mandíbula.", "Marca cómo te sientes.")),
        Routine("r2", "Reset con roll-on", "Recomendada por Aura para regular la ansiedad de golpe.", "Ansiedad", "Regular estrés respiratorio.", 3, listOf("Roll-on relajante 10 ml", "Tarjetas de respiración"), false, listOf("Aplica roll-on en muñecas y sienes.", "Elige una tarjeta de respiración guiada.", "Respira siguiendo el ritmo sugerido.", "Registra tu nivel de estrés ahora.")),
        Routine("r3", "Desahógate en papel", "Recomendada por Aura para vaciar una mente saturada.", "Saturación", "Soltar carga mental.", 5, listOf("Journal premium", "Lapicero MyAura"), false, listOf("Abre el diario de hojas limpias.", "Escribe sin filtro todo lo que sientes.", "No corrijas ni pienses en ortografía.", "Cierra el diario escribiendo: 'Hoy necesito...'")),
        Routine("r4", "Infusión consciente", "Recomendada por un momento de descanso a la mitad del día.", "Cansancio", "Crear una pausa tranquila.", 10, listOf("Termo MyAura", "Taza o infusión"), false, listOf("Prepara una bebida templada o té.", "Sostén el termo y siéntete presente.", "Toma pequeños sorbos sin usar el celular.", "Declara una intención para la tarde.")),
        Routine("r5", "Ritual con vela", "Recomendada por Aura para desacelerar nuestro ritmo.", "Sobrecarga", "Crear calma y calidez ambiental.", 10, listOf("Vela aromática de soya", "Journal premium"), false, listOf("Enciende la vela aromática.", "Observa la llama durante un minuto consciente.", "Escribe lo que decides dejar ir de tu día.", "Cierra con una respiración profunda.")),
        Routine("r6", "Cierre nocturno profundo", "Recomendada antes de acostarte para regular el sueño.", "Insomnio", "Preparar descanso regenerativo.", 15, listOf("Vela grande doble mecha", "Spray relajante", "Antifaz", "Journal premium"), false, listOf("Rocía tu almohada con spray relajante.", "Enciende la vela grande por unos minutos.", "Escribe 2 pendientes para despejar el cerebro.", "Ponte el antifaz de seda y prepárate para soñar.")),
        Routine("r7", "Enfoque antes de estudiar", "Recomendada por Aura para mejorar la atención sostenida.", "Falta de enfoque", "Organizar tus prioridades.", 5, listOf("Spray relajante", "Journal premium", "Plantilla tracking de hábitos"), false, listOf("Atomiza tu mesa de estudio.", "Escribe las 3 tareas principales en tu papel.", "Elige solo una de ellas para iniciar.", "Inicia un cronómetro de enfoque de 25 minutos.")),
        Routine("r8", "Pausa con difusor", "Recomendada al llegar del trabajo para un cambio de ambiente.", "Saturación laboral", "Transformar el entorno.", 10, listOf("Difusor aromaterapia USB", "Aceite esencial extra premium"), false, listOf("Enciende tu difusor USB con agua fresca.", "Agrega 3 gotas de aceite esencial de bergamota.", "Siéntate cómodamente con la espalda recta.", "Siente cómo el aroma relaja tus facciones.")),
        Routine("r9", "Recuperación mental", "Recomendada por Aura si tienes dolor de cabeza leve o pesadez.", "Agotamiento", "Aliviar saturación cerebral.", 5, listOf("Masajeador de cabeza", "Inhalador nasal de aromaterapia"), false, listOf("Desliza el masajeador capilar suavemente.", "Inhala profundamente los aceites esenciales.", "Cierra los ojos disfrutando las sensaciones de hormigueo.", "Sostén una actitud de agradecimiento por 2 minutos.")),
        Routine("r10", "Gratitud mínima", "Recomendada para recablear tu optimismo diario.", "Desánimo", "Cambiar el enfoque mental.", 3, listOf("Journal premium", "Lapicero MyAura"), false, listOf("Anota 3 cosas pequeñas agradables ocurridas hoy.", "Agradece un privilegio o aspecto que sueles ignorar.", "Respira y guarda el diario en tu espacio seguro.")),
        Routine("r11", "Pausa sin kit", "Ideal para hacer en cualquier lugar, no necesitas ningún elemento.", "Estrés", "Volver al presente.", 2, emptyList(), true, listOf("Dirige tu mirada a tu entorno inmediato.", "Identifica y nombra 3 cosas de color azul o pastel.", "Suelta la tensión acumulada en tu mandíbula.", "Inhala hondo y espira muy despacio.")),
        Routine("r12", "Caminata consciente", "Recomendada por Aura para mover el cuerpo y despejar ideas.", "Bloqueo creativo", "Mover el cuerpo tranquilamente.", 10, emptyList(), true, listOf("Sal a caminar al aire libre.", "Observa el vaivén de tus pasos y tu postura.", "Prohibido revisar redes sociales mientras andas.", "Nota la frescura del viento al regresar.")),
        Routine("r13", "Ritual post-entreno", "Recomendada para regenerar la energía tras el ejercicio físico.", "Cansancio físico", "Recuperar cuerpo y mente.", 10, listOf("Termo MyAura", "Roll-on relajante 10 ml", "Journal premium"), false, listOf("Hidrátate con agua fría de tu termo.", "Coloca roll-on relajante en tus músculos cansados.", "Escribe cómo se siente tu cuerpo después del esfuerzo.", "Cierra los ojos respirando con sosiego.")),
        Routine("r14", "Pausa de frustración", "Recomendada por Aura tras una mala noticia o bloqueos.", "Frustración", "Canalizar frustración de forma sana.", 5, listOf("Journal premium", "Objeto anti-estrés"), false, listOf("Aprieta firmemente el objeto anti-estrés.", "Descarga en el papel qué es exactamente lo que te frustra.", "Determina qué partes de ello puedes controlar tú.", "Elige una acción mínima factible ahora mismo.")),
        Routine("r15", "Rutina para empezar suave", "Recomendada si despertaste sin ganas o muy cansado.", "Desánimo matutino", "Iniciar el día sin prisa.", 5, listOf("Inhalador nasal de aromaterapia", "Termo MyAura"), false, listOf("Estando aún en la cama, usa tu inhalador.", "Levántate y tómate un vaso de agua.", "Date permiso para avanzar hoy de forma progresiva.", "Escoge solo un quehacer sencillo para arrancar.")),
        Routine("r16", "Ritual de domingo", "Dedicada a planificar tu semana con calma mental.", "Desorganización", "Definir prioridades amables.", 15, listOf("Journal premium", "Plantilla tracking de hábitos", "Vela aromática de soya"), false, listOf("Enciende la vela de soya.", "Evalúa tus hábitos de la semana recién culminada.", "Selecciona solo 3 prioridades para la siguiente semana.", "Escribe tu mantra de bienestar para sostenerte.")),
        Routine("r17", "Descanso de pantalla", "Recomendada antes de cenar para desconectarte.", "Saturación digital", "Liberarse de la dopamina online.", 10, listOf("Bolsa aromática", "Journal premium"), false, listOf("Pon tu celular en modo avión en otra habitación.", "Sostén la bolsa aromática y siente su aroma.", "Escribe lo que más te gustó de interactuar cara a cara hoy.", "Permítete aburrirte un momento con amabilidad.")),
        Routine("r18", "Pausa de amor propio", "Recomendada si te sientes culpable o te estás juzgando.", "Autocrítica", "Tratarte con autocompasión.", 5, listOf("Journal premium", "Vela aromática de soya"), false, listOf("Prende tu vela aromática.", "Escribe una frase tierna que le dirías a un buen amigo.", "Léela en voz alta dirigiéndola a ti.", "Respira y repite mentalmente: 'Hago lo mejor que sé'.")),
        Routine("r19", "Organización mental rápida", "Perfecta para ordenar pendientes caóticos.", "Caos mental", "Ordenar pendientes acumulados.", 7, listOf("Journal premium", "Lapicero MyAura"), false, listOf("Haz una lista caótica de todo lo que crees que tienes pendiente.", "Subraya con un círculo las únicas 2 verdaderamente urgentes.", "Guarda el papel para mañana con la certeza de que bastará.", "Siente el alivio de haberlo descargado.")),
        Routine("r20", "Ritual de reconexión", "La experiencia máxima de relajación sensorial con tu kit.", "Desconexión personal", "Reconectar con tu ser esencial.", 15, listOf("Difusor aromaterapia USB", "Vela grande doble mecha", "Journal premium", "Inhalador nasal de aromaterapia"), false, listOf("Enciende el difusor con aroma extra premium y la vela.", "Usa el inhalador inhalando profundamente por la nariz.", "Responde libremente: '¿Qué necesita escuchar mi alma hoy?'", "Termina con una meditación silenciosa de 5 minutos."))
    )

    val workshops = listOf(
        Workshop("w1", "Estrés cotidiano", "Identifica tus detonantes diarios y aprende a reaccionar con pausas reguladoras.", "identificar detonantes de estrés y crear pausas prácticas.", 89900.0, 26970.0, false, listOf(
            WorkshopModule("m1_1", "¿Qué es el estrés cotidiano?", "12 min", "video_sim_url", "El estrés no es un enemigo, sino un mensajero que nos avisa de un desbalance.", "mapa_estres.pdf", "Dibuja tu mapa actual de alertas en el cuerpo.", "¿Qué señales físicas te indican que estás superando tu límite de tranquilidad?", false),
            WorkshopModule("m1_2", "Señales físicas y emocionales", "10 min", "video_sim_url", "Identificar los síntomas antes del colapso emocional es la clave de la maestría.", "sintomas_alerta.pdf", "Registra durante 2 días cuándo sientes taquicardia o cefalea.", "¿Sueles ignorar las señales corporales por cumplir con metas externas?", false),
            WorkshopModule("m1_3", "Pausas rápidas para bajar el ritmo", "15 min", "video_sim_url", "Una pausa de 2 minutos oxigena el cerebro y reduce el cortisol sanguíneo.", "pausas_rapidas.pdf", "Aplica un reset con Roll-on en tu próxima pausa.", "¿Qué excusa sueles poner para no regalarte 2 minutos de respiro?", false),
            WorkshopModule("m1_4", "Plan personal de manejo de estrés", "18 min", "video_sim_url", "Estructura un plan de acción basado en alertas personalizadas.", "plan_antistress.pdf", "Crea tu plantilla física de manejo de pánico y estrés.", "¿Qué elemento de tu Kit usarás primero cuando sientas sobrecarga?", false),
            WorkshopModule("m1_5", "Evaluación Final y Cierre", "15 min", "video_sim_url", "Felicidades. Es momento de consolidar tus aprendizajes de bienestar.", "certificado_estres.pdf", "Completa la prueba de 5 preguntas rápidas.", "¿Qué cambio principal decides implementar a partir de hoy mismo?", false)
        )),
        Workshop("w2", "Hábitos y rutina", "Aprende a construir hábitos duraderos alineados a tu ciclo natural de energía.", "construir una rutina antiestrés realista.", 89900.0, 26970.0, false, listOf(
            WorkshopModule("m2_1", "La ciencia de la formación de hábitos", "10 min", "video_sim_url", "Los hábitos se construyen con pequeñas señales y recompensas sencillas.", "habits_loop.pdf", "Elige un hábito pequeño y define su disparador físico.", "¿Cuál es el principal saboteador de tus nuevas rutinas?", false),
            WorkshopModule("m2_2", "Rutina mínima viable", "12 min", "video_sim_url", "Es mejor hacer un hábito por 2 minutos que no hacerlo por falta de tiempo.", "micro_rutinas.pdf", "Diseña una rutina de mañana que tome solo 5 minutos.", "¿Cómo puedes simplificar tu mañana para no iniciar apresurado?", false),
            WorkshopModule("m2_3", "Rituales de mañana, tarde y noche", "14 min", "video_sim_url", "Sincroniza tus transiciones diarias con el uso táctil de tu Kit.", "rituales_diarios.pdf", "Define un anclaje para tu transición laboral-hogar.", "¿Qué te ayuda más a desconectarte por las noches?", false),
            WorkshopModule("m2_4", "Plan de 7 días MyAura", "15 min", "video_sim_url", "Establece un calendario de experimentación amigable.", "plan_7dias.pdf", "Llena tus primeros 3 días en el habit tracker.", "¿Estás dispuesto a aceptar imperfecciones en tu proceso de cambio?", false),
            WorkshopModule("m2_5", "Prueba Evaluativa y Rutina Sostenible", "10 min", "video_sim_url", "Felicitaciones, has diseñado tu camino. Evaluemos tu consistencia.", "habito_sostenido.pdf", "Completa el test sobre bucles neuronales.", "¿Cuál es tu mantra para los días en que no logres completar la rutina?", false)
        )),
        Workshop("w3", "Productividad consciente", "Produce resultados con excelencia sin recurrir al agotamiento mental.", "mejorar el enfoque sin saturación mental.", 99900.0, 29970.0, false, listOf(
            WorkshopModule("m3_1", "Productividad vs Sobrecarga", "11 min", "video_sim_url", "Ser productivo no es hacer más, sino hacer lo que importa con calma.", "foco_vs_caos.pdf", "Elige tu tarea roca de mañana.", "¿Estás confundiendo estar ocupado con ser realmente productivo?", false),
            WorkshopModule("m3_2", "Preparar la mente antes del trabajo", "12 min", "video_sim_url", "Un espacio ordenado es el reflejo de un cerebro en paz.", "escritorio_zen.pdf", "Ordena tu escritorio y esparce spray aromático.", "¿Qué distracción visual sueles tener en tu área de labor?", false),
            WorkshopModule("m3_3", "Bloques de enfoque y pausas Pomodoro", "15 min", "video_sim_url", "Trabajar en bloques cerrados reduce la fatiga mental.", "pomodoro_mindful.pdf", "Ejecuta un bloque de 25 minutos con temporizador.", "¿Respetas tus pausas o sigues de corrido hasta agotarte?", false),
            WorkshopModule("m3_4", "Cierre mental de jornada", "13 min", "video_sim_url", "Escribir tus tareas pendientes de mañana le avisa al cerebro que puede descansar.", "cierre_jornada.pdf", "Anota tus pendientes y cierra tu computador.", "¿Te llevas preocupaciones laborales a la mesa de cenar?", false),
            WorkshopModule("m3_5", "Consolidación de Enfoque y Examen", "12 min", "video_sim_url", "Dominas tu atención. Apliquemos la evaluación de productividad consciente.", "enfoque_excelencia.pdf", "Resuelve el test de gestión de interrupciones.", "¿Qué límites vas a poner a tus notificaciones de ahora en adelante?", false)
        )),
        Workshop("w4", "Regulación emocional", "Obtén la sabiduría de procesar tus emociones con calma y templanza.", "reconocer emociones y responder mejor.", 99900.0, 29970.0, false, listOf(
            WorkshopModule("m4_1", "El mapa de las emociones", "15 min", "video_sim_url", "La emoción es energía en movimiento. Sentirla es de valientes.", "mapa_emocional.pdf", "Reconoce en qué zona física sientes el enojo o miedo.", "¿A qué emoción le tienes más resistencia y por qué?", false),
            WorkshopModule("m4_2", "Nombrar sin juzgar", "12 min", "video_sim_url", "Darle nombre a lo que sientes disminuye su intensidad neurológica.", "nombrar_emocion.pdf", "Escribe libremente: 'Hoy me siento... porque...'", "¿Sueles catalogar tus emociones en buenas o malas?", false),
            WorkshopModule("m4_3", "Respiración y escritura emocional", "15 min", "video_sim_url", "La escritura terapéutica drena el dolor y aporta claridad racional.", "escritura_catarsis.pdf", "Realiza 5 min de escritura sin filtro y destrúyela al terminar.", "¿Qué verdades descubriste al escribir sin restricciones?", false),
            WorkshopModule("m4_4", "Mi protocolo de saturación", "15 min", "video_sim_url", "Crea un plan de rescate para cuando sientas que vas a estallar.", "protocolo_rescate.pdf", "Delinea tus 3 pasos obligatorios ante una crisis.", "¿Quién es tu red de apoyo en momentos de alta vulnerabilidad?", false),
            WorkshopModule("m4_5", "Evaluación Emocional y Cierre de Módulo", "10 min", "video_sim_url", "Has integrado tus emociones. Resolvamos el cuestionario final.", "diploma_emociones.pdf", "Responde el test de inteligencia emocional de MyAura.", "¿Cómo vas a tratarte con mayor gentileza a partir de hoy?", false)
        )),
        Workshop("w5", "Descanso y desconexión", "Diseña el ritual nocturno definitivo para dormir profundo y regenerar tu cuerpo.", "crear ritual nocturno para cerrar el día.", 89900.0, 26970.0, false, listOf(
            WorkshopModule("m5_1", "El insomnio digital", "13 min", "video_sim_url", "La luz azul de las pantallas destruye tu producción de melatonina.", "luz_azul.pdf", "Deja el celular fuera del cuarto 1 hora antes de dormir hoy.", "¿Qué es lo último que ves antes de cerrar tus ojos por la noche?", false),
            WorkshopModule("m5_2", "Cerrar el día con gratitud", "11 min", "video_sim_url", "Dormir agradecido relaja el sistema nervioso simpático.", "gratitud_nocturna.pdf", "Escribe 3 cosas maravillosas de tu día.", "¿Qué fue lo más gratificante de tu jornada hoy?", false),
            WorkshopModule("m5_3", "Ritual aromático nocturno", "14 min", "video_sim_url", "Prepara tu habitación con aromas que induzcan el sueño profundo.", "aromaterapia_sueno.pdf", "Usa tu spray de almohada y tu antifaz de seda.", "¿Qué aromas asocia tu cerebro con el hogar y la seguridad?", false),
            WorkshopModule("m5_4", "Ejercicios de respiración relajante", "15 min", "video_sim_url", "La respiración 4-7-8 reduce el ritmo cardíaco y promueve la somnolencia.", "respiracion_dormir.pdf", "Practica la respiración guiada nocturna.", "¿Lograste percibir el cambio de temperatura al respirar lento?", false),
            WorkshopModule("m5_5", "Evaluación Nocturna y Certificación", "12 min", "video_sim_url", "Tu descanso es sagrado. Realicemos la evaluación final de ciclo.", "certificado_sueno.pdf", "Completa la prueba sobre higiene del sueño.", "¿Cuál será tu mayor compromiso para cuidar tus noches?", false)
        )),
        Workshop("w6", "Bienestar social y límites", "Protege tu paz interna aprendiendo a decir 'no' sin culpa.", "cuidar la energía en relaciones, estudio y trabajo.", 99900.0, 29970.0, false, listOf(
            WorkshopModule("m6_1", "Drenaje de energía", "10 min", "video_sim_url", "Tu tiempo y tu energía son limitados. Elígelos con sabiduría.", "fuga_energia.pdf", "Anota qué personas o tareas agotan tu día.", "¿Qué actividades te restan más paz mental?", false),
            WorkshopModule("m6_2", "Límites sin culpa", "12 min", "video_sim_url", "Un 'no' a otros suele ser un 'sí' a ti mismo y a tu salud.", "limites_sanos.pdf", "Practica decir 'no' ante una petición poco prioritaria.", "¿Por qué te asusta tanto decepcionar a los demás?", false),
            WorkshopModule("m6_3", "Comunicación compasiva", "14 min", "video_sim_url", "Puedes poner límites firmes con palabras amables y empáticas.", "asertividad_aura.pdf", "Escribe un mensaje de respuesta asertivo utilizando el yo.", "¿De qué manera hablas cuando estás cansado o frustrado?", false),
            WorkshopModule("m6_4", "Mi plan de blindaje energético", "15 min", "video_sim_url", "Establece reglas de oro para proteger tu enfoque y descanso.", "blindaje.pdf", "Crea tus 3 leyes inquebrantables de límites personales.", "¿Qué límites necesitas colocar en tu estudio o empleo ahora mismo?", false),
            WorkshopModule("m6_5", "Examen de Asertividad y Cierre de Taller", "15 min", "video_sim_url", "Has aprendido a valorarte. Resolvamos el test final.", "diploma_limites.pdf", "Responde el cuestionario de asertividad aplicada.", "¿Cómo te sientes al priorizar tu bienestar por sobre el compromiso?", false)
        ))
    )

    val masterclasses = listOf(
        Masterclass("m1", "Cómo calmar tu mente en días de mucho estrés", "Herramientas de rescate cognitivo y aromaterapia.", 45, "María José Restrepo", true, 39900.0, 11970.0, "video_mc1", "res_mc1.pdf"),
        Masterclass("m2", "Rutinas antiestrés para estudiantes", "Organización del tiempo sin sacrificar horas de sueño.", 50, "Camilo Soto", true, 39900.0, 11970.0, "video_mc2", "res_mc2.pdf"),
        Masterclass("m3", "Cómo dormir mejor sin obligarte a dormir", "La higiene del sueño y la melatonina natural.", 60, "Dra. Sofía Martínez", true, 49900.0, 14970.0, "video_mc3", "res_mc3.pdf"),
        Masterclass("m4", "Productividad sin agotamiento", "Alinear metas con ritmos circadianos.", 55, "Juan Pablo Gómez", true, 49900.0, 14970.0, "video_mc4", "res_mc4.pdf"),
        Masterclass("m5", "Respiración consciente y el nervio vago", "Activación del sistema parasimpático en directo.", 65, "Daniela Giraldo", false, 59900.0, 17970.0, "video_mc5", "res_mc5.pdf"),
        Masterclass("m6", "Journaling emocional: escribir para soltar", "El arte de limpiar la psique con papel y tinta.", 45, "Laura Restrepo", true, 39900.0, 11970.0, "video_mc6", "res_mc6.pdf"),
        Masterclass("m7", "Cómo crear una rutina de bienestar realista", "Diseño paso a paso de tu propio ritual diario.", 50, "Mateo Ossa", true, 39900.0, 11970.0, "video_mc7", "res_mc7.pdf"),
        Masterclass("m8", "Autocuidado para personas con agenda ocupada", "Optimización de micro-pausas útiles de 2 minutos.", 55, "Ana María Vélez", true, 49900.0, 14970.0, "video_mc8", "res_mc8.pdf"),
        Masterclass("m9", "Aromaterapia y rituales sensoriales", "Cómo usar aceites e inciensos para inducir paz.", 60, "Estefanía Pardo", true, 49900.0, 14970.0, "video_mc9", "res_mc9.pdf"),
        Masterclass("m10", "Cómo volver a ti tras una crisis", "Anclaje corporal y grounding en momentos de caos.", 70, "Santiago Tobón", false, 59900.0, 17970.0, "video_mc10", "res_mc10.pdf")
    )

    val physicalEvents = listOf(
        PhysicalEvent("pe1", "Pilates & Pausa MyAura", "Clase consciente de estiramiento y aromaterapia en el Poblado.", "Gimnasio Boutique Poblado, Medellín", "12 de Mayo - 10:00 AM", 25, 49900.0, 14970.0),
        PhysicalEvent("pe2", "Yoga para soltar el estrés", "Práctica de vinyasa suave al aire libre rodeados de árboles.", "Parque Arví, Medellín", "18 de Mayo - 8:30 AM", 30, 39900.0, 11970.0),
        PhysicalEvent("pe3", "Meditación guiada al atardecer", "Práctica zen con cuencos tibetanos contemplando las montañas.", "Mirador San Felix, Medellín", "30 de Mayo - 5:30 PM", 20, 39900.0, 11970.0),
        PhysicalEvent("pe4", "Caminata consciente y brunch", "Senderismo de desconexión y alimentación nutritiva.", "Reserva Envigado, Medellín", "4 de Junio - 7:00 AM", 15, 69900.0, 20970.0),
        PhysicalEvent("pe5", "Matcha, café & charla consciente", "Taller de preparación de matcha y diálogo sobre salud mental.", "Café Aliado Laureles, Medellín", "15 de Junio - 4:00 PM", 12, 45000.0, 13500.0)
    )

    val activations = listOf(
        Activation("a1", "Pausa Aura: Ritual Express", "25 de Mayo - 3:00 PM", "Café Aliado Poblado, Medellín", 25, listOf("Bienvenida y presentación", "Escaneo de invitación QR", "Mini check-in con Aura", "Ritual sensorial guiado con Kit", "Descarga de PWA y obsequio"), "Llevar ropa cómoda y tu termo MyAura.")
    )

    val achievements = listOf(
        Achievement("ach1", "7 días de check-in", "Has completado tu autoregistro diario por una semana entera sin fallar.", true, "calendar_today"),
        Achievement("ach2", "30 días de autocuidado", "Un mes completo de dedicación y crecimiento personal con MyAura.", false, "self_improvement"),
        Achievement("ach3", "Primera rutina nocturna", "Desconectaste tu mente y preparaste tu descanso con un ritual sutil.", true, "bedtime"),
        Achievement("ach4", "Primer taller completado", "Has finalizado el Taller de Estrés Cotidiano y aprobado tu evaluación.", false, "school"),
        Achievement("ach5", "Kit RITUAL conectado", "Lograste emparejar tu difusor de bienestar y tus elementos premium.", true, "inventory_2"),
        Achievement("ach6", "100 minutos de calma", "Has acumulado más de 100 minutos en pausas y meditaciones.", true, "timer")
    )
}
