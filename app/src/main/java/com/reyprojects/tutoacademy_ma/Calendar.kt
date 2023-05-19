package com.reyprojects.tutoacademy_ma

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyprojects.tutoacademy_ma.ui.theme.WeekScheduleTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt



@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun CalendarMobile() {

    WeekScheduleTheme {
        Surface(color = MaterialTheme.colors.background) {
            if (sampleEvents.isNotEmpty()){

                Schedule(events = sampleEvents)

            }
            else {

                Column(modifier =Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .fillMaxSize()
                    .verticalScroll(
                    rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painterResource(R.drawable.doubtlogo),
                        contentDescription = null,
                        modifier = Modifier
                            .height(247.dp)
                            .width(322.dp).align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "No tienes ninguna reunión programada",
                        fontSize = 30.sp,
                        color = Color(251, 196, 3),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    
                }
                
            }

        }
    }

}


data class Event(
    val tutor: String,
    val accepted: Boolean,
    val createdAt: LocalDateTime,
    val id: Int,
    val requestedBy: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val message: String?,
)

@RequiresApi(Build.VERSION_CODES.O)
val EventTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")!!

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicEvent(
    event: Event,
    modifier: Modifier = Modifier,
) {
    var show by rememberSaveable{ mutableStateOf(false )}
    Column(
        modifier = modifier
            .padding(end = 2.dp, bottom = 2.dp)
            .background(
                colorResource(id = R.color.background_event),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(4.dp)

    ) {

        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            style = MaterialTheme.typography.caption,
        )
        TextButton(onClick = { show = true }) {

            Text(
                text = event.tutor,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }

        Details(show, event) { show = false }




        if (event.message != null) {
            Text(
                text = event.message,
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Details(show:Boolean, event: Event, onConfirm: ()-> Unit){
    if (show){
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = "OK")
                }
            },
            title = { Text(text = "Detalles del evento") },
            text = {
                Column{
                    Text(text = "Nombre del tutor: " + event.tutor)
                    Text(text = "Fecha de creación:  Aqui va el tema a dictar")
                    Text(text = "Inicia: " + event.start.format(EventTimeFormatter))
                    Text(text = "Termina: " + event.end.format(EventTimeFormatter))
                    Text(text = "Mensaje: " + event.message)
                    Text(text = "Solicitante: " + event.requestedBy)

                } }
        )
    }
}


private class EventDataModifier(
    val event: Event,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = event
}

private fun Modifier.eventData(event: Event) = this.then(EventDataModifier(event))

@RequiresApi(Build.VERSION_CODES.O)
val DayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, LLLL d")!!

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicDayHeader(
    day: LocalDate,
    modifier: Modifier = Modifier,
) {
    Text(
        text = day.format(DayFormatter),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleHeader(
    minDate: LocalDate,
    maxDate: LocalDate,
    dayWidth: Dp,
    modifier: Modifier = Modifier,
    dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
) {
    Row(modifier = modifier) {
        val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
        repeat(numDays) { i ->
            Box(modifier = Modifier.width(dayWidth)) {
                dayHeader(minDate.plusDays(i.toLong()))
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
private val HourFormatter = DateTimeFormatter.ofPattern("h a")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier,
) {
    Text(
        text = time.format(HourFormatter),
        modifier = modifier
            .padding(4.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    modifier: Modifier = Modifier,
    label: @Composable (time: LocalTime) -> Unit = { BasicSidebarLabel(time = it) },
) {
    Column(modifier = modifier) {
        val startTime = LocalTime.MIN
        repeat(24) { i ->
            Box(modifier = Modifier.height(hourHeight)) {
                label(startTime.plusHours(i.toLong()))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Schedule(
    
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    dayHeader: @Composable (day: LocalDate) -> Unit = { BasicDayHeader(day = it) },
    minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
    maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate(),
) {
        val dayWidth = 256.dp
        val hourHeight = 64.dp
        val verticalScrollState = rememberScrollState()
        val horizontalScrollState = rememberScrollState()
        var sidebarWidth by remember { mutableStateOf(0) }
        Column(modifier = modifier) {
            ScheduleHeader(
                minDate = minDate,
                maxDate = maxDate,
                dayWidth = dayWidth,
                dayHeader = dayHeader,
                modifier = Modifier
                    .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
                    .horizontalScroll(horizontalScrollState)
            )
            Row {
                ScheduleSidebar(
                    hourHeight = hourHeight,
                    modifier = Modifier
                        .verticalScroll(verticalScrollState)
                        .onGloballyPositioned { sidebarWidth = it.size.width }
                )
                BasicSchedule(
                    events = events,
                    eventContent = eventContent,
                    minDate = minDate,
                    maxDate = maxDate,
                    dayWidth = dayWidth,
                    hourHeight = hourHeight,
                    modifier = Modifier
                        .verticalScroll(verticalScrollState)
                        .horizontalScroll(horizontalScrollState)
                )
            }
        }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicSchedule(
    events: List<Event>,
    modifier: Modifier = Modifier,
    eventContent: @Composable (event: Event) -> Unit = { BasicEvent(event = it) },
    minDate: LocalDate = events.minByOrNull(Event::start)!!.start.toLocalDate(),
    maxDate: LocalDate = events.maxByOrNull(Event::end)!!.end.toLocalDate(),
    dayWidth: Dp,
    hourHeight: Dp,
) {
    val numDays = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    val dividerColor = if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray
    Layout(
        content = {
            events.sortedBy(Event::start).forEach { event ->
                Box(modifier = Modifier.eventData(event)) {
                    eventContent(event)
                }
            }
        },
        modifier = modifier
            .drawBehind {
                repeat(23) {
                    drawLine(
                        dividerColor,
                        start = Offset(0f, (it + 1) * hourHeight.toPx()),
                        end = Offset(size.width, (it + 1) * hourHeight.toPx()),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                repeat(numDays - 1) {
                    drawLine(
                        dividerColor,
                        start = Offset((it + 1) * dayWidth.toPx(), 0f),
                        end = Offset((it + 1) * dayWidth.toPx(), size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) { measureables, constraints ->
        val height = hourHeight.roundToPx() * 24
        val width = dayWidth.roundToPx() * numDays
        val placeablesWithEvents = measureables.map { measurable ->
            val event = measurable.parentData as Event
            val eventDurationMinutes = ChronoUnit.MINUTES.between(event.start, event.end)
            val eventHeight = ((eventDurationMinutes / 60f) * hourHeight.toPx()).roundToInt()
            val placeable = measurable.measure(constraints.copy(minWidth = dayWidth.roundToPx(), maxWidth = dayWidth.roundToPx(), minHeight = eventHeight, maxHeight = eventHeight))
            Pair(placeable, event)
        }
        layout(width, height) {
            placeablesWithEvents.forEach { (placeable, event) ->
                val eventOffsetMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, event.start.toLocalTime())
                val eventY = ((eventOffsetMinutes / 60f) * hourHeight.toPx()).roundToInt()
                val eventOffsetDays = ChronoUnit.DAYS.between(minDate, event.start.toLocalDate()).toInt()
                val eventX = eventOffsetDays * dayWidth.roundToPx()
                placeable.place(eventX, eventY)
            }
        }
    }
}

