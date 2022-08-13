package net.nexiesy

import java.time.{Clock, ZoneId}
import java.time.format.{DateTimeFormatter, TextStyle}
import java.time.zone.ZoneRulesProvider
import java.util.Locale

import scala.io.StdIn.readLine

object TimeFinder {

  private var dateFormater = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss z")
  def main(args: Array[String]): Unit = {
    println("Commands: ")
    println("- list")
    println("- time <zone>")
    start()
  }

  def start(): Unit ={
    val command = readLine("> ")
    commandParse(command);
  }

  def commandParse(command: String): Unit ={
    if (command.toLowerCase().equals("list")){
      ZoneRulesProvider.getAvailableZoneIds.toArray().foreach{
        case (key) => println(key)
      }
    }

    if (command.toLowerCase().startsWith("time")){
      try {
        val zone = command.split(" ").lift(1).get;
        val z = ZoneId.of(zone);
        dateFormater = dateFormater.withZone(z);

        val clock = Clock.system(z);
        println("Timezone: %s".format(z.getDisplayName(TextStyle.FULL, Locale.ENGLISH)))
        println("Zone: %s".format(zone))
        println("Time: %s".format(dateFormater.format(clock.instant())))
      }catch{ case e: Exception => println("Unknown zone") }
    }
    start();
  }
}
