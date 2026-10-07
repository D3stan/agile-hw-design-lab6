import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
// Slide "Getting Started": imports and the class header
import org.scalacheck.{Arbitrary, Gen, Shrink}
import org.scalatestplus.scalacheck._
import chisel3.simulator.PeekPokeAPI

abstract class CounterBehaviour[T <: Module with HasCounterOut](
    name: String, max: Int)(gen: => T)
    extends AnyFlatSpec
    with ChiselScalatestTester
    with ScalaCheckPropertyChecks {

    behavior of name

    "A counter" should "start at 0" in {
        test(gen) { c =>
            c.out.expect(0.U)
        }
    }

    it should "count up once" in {
        test(gen) { c =>
            c.clock.step()
            c.out.expect(1.U)
        }   
    }

    it should "count up unti its max" in {
        test(gen) { c =>
            for (i <- 0 until max) {
                c.out.expect(i.U)
                c.clock.step()
            }
            c.out.expect(max.U)
        }
    }

    it should "overflow" in {
        test(gen) { c =>
            c.clock.step(max + 1)
            c.out.expect(0.U)
        }
    }
}

class FixedCounterTest   extends CounterBehaviour("FixedCounter(8)", 255)(new FixedCounter(8))
class GenericCounterTest extends CounterBehaviour("GenericCounter(10)", 10)(new GenericCounter(10))