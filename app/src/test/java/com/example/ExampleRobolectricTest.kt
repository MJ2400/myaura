package com.example

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.example.ui.MyAuraAppContent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ExampleRobolectricTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("MyAura", appName)
  }

  @Test
  fun `test activity launch`() {
    ActivityScenario.launch(MainActivity::class.java).use { scenario ->
      assertNotNull(scenario)
    }
  }

  @Test fun render_onboarding() = renderScreen(Screen.Onboarding)
  @Test fun render_survey() = renderScreen(Screen.Survey)
  @Test fun render_main() = renderScreen(Screen.Main)
  @Test fun render_checkin() = renderScreen(Screen.CheckIn)
  @Test fun render_checkout() = renderScreen(Screen.CheckOut)
  @Test fun render_activeroutine() = renderScreen(Screen.ActiveRoutine)
  @Test fun render_chat() = renderScreen(Screen.Chat)
  @Test fun render_workshopdetail() = renderScreen(Screen.WorkshopDetail)
  @Test fun render_masterclasses() = renderScreen(Screen.MasterclassesList)
  @Test fun render_events() = renderScreen(Screen.EventsList)
  @Test fun render_specialactivities() = renderScreen(Screen.SpecialActivities)
  @Test fun render_calendar() = renderScreen(Screen.Calendar)
  @Test fun render_corporate() = renderScreen(Screen.Corporate)
  @Test fun render_progress() = renderScreen(Screen.Progress)
  @Test fun render_tienda() = renderScreen(Screen.Tienda)

  private fun renderScreen(screen: Screen) {
    val viewModel = MyAuraViewModel().apply { updateScreen(screen) }
    composeTestRule.setContent {
      MyAuraAppContent(viewModel = viewModel)
    }
    composeTestRule.waitForIdle()
  }

  @Test
  fun `test viewModel actions`() {
    val viewModel = MyAuraViewModel()
    
    // 1. Survey answers
    viewModel.setSurveyAnswer(1, "20-30")
    viewModel.setSurveyAnswer(9, "7")
    viewModel.setSurveyAnswer(10, listOf("Calma", "Enfoque"))
    
    // 2. Complete check-in
    viewModel.completeCheckIn(
        energy = "Alta",
        stress = 3,
        wakeFeeling = "Fresco",
        sleepQuality = "Excelente",
        sleepDuration = "8h",
        dreams = "Ninguno",
        wakeRested = "Sí",
        needsToday = "Calma",
        remindPause = true,
        todosList = emptyList()
    )
    
    // 3. Complete check-out
    viewModel.completeCheckOut("Excelente", "Dormir temprano")
    
    // 4. Shop actions
    val shopItem = StaticData.kits.first()
    viewModel.addToCart(shopItem)
    assertEquals(1, viewModel.state.value.shoppingCart.size)
    viewModel.removeFromCart(shopItem)
    assertEquals(0, viewModel.state.value.shoppingCart.size)
    
    viewModel.addToCart(shopItem)
    viewModel.simulatePurshase()
    
    // 6. Corporate
    viewModel.toggleCorporateBreak()
    
    // 7. select routine and tick
    val routine = StaticData.independentRoutines.first()
    viewModel.selectRoutine(routine)
    viewModel.tickRoutineStep()
    
    // 8. Reset
    viewModel.resetSession()
  }
}
