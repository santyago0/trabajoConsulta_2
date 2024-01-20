package bimestre2

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object ejemplo_Reactivo {
  def main(args: Array[String]): Unit = {
    // Se crea un sistema de actores de Akka
    implicit val system: ActorSystem = ActorSystem("EjemploReactiveScala")

    // Declaramos un flujo de número del 1 - 20
    val flujoNumeros: Source[Int, _] = Source(1 to 20)

    // Filtramos los números que son pares, y lo transformamos en un String
    // para poner un mensaje
    val filtradoPares = flujoNumeros
      .filter(x => x%2 == 0)
      .map(x => s"$x es par")

    // Se define un valor que será el destino (Sink) que imprime los numeros
    // pares transformados a String con un mensaje
    val presentacion = Sink.foreach[String](println)

    // Hacemos la conexión con el flujo de entrada (flujoNumeros) con el destino
    // (presentacion)
    filtradoPares.runWith(presentacion)

    // Hacemos una espera de 1 segundo para que se termine de procesar el flujo y
    // se impriman los resultados
    Thread.sleep(1000)

    // Se fiinaliza el sistema de actores
    system.terminate()
  }
}
