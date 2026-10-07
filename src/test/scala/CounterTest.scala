import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
// Slide "Getting Started": imports and the class header
import org.scalacheck.{Arbitrary, Gen, Shrink}
import org.scalatestplus.scalacheck._
import chisel3.simulator.PeekPokeAPI

class CounterTest extends AnyFlatSpec
    with ChiselScalatestTester
    with ScalaCheckPropertyChecks {

    "A counter" should "start at 0" in {
        test(new FixedCounter(8)) { c =>
            c.io.out.expect(0.U)
        }
    }

    it should "count up once" in {
        test(new FixedCounter(8)) { c =>
            c.clock.step()
            c.io.out.expect(1.U)
        }   
    }

    it should "count up many times" in {
        test(new FixedCounter(8)) { c =>
            for (i <- 0 to 254) {
                c.io.out.expect(i)
                c.clock.step()
                c.io.out.expect(i + 1)
            }
        }
    }

    it should "overflow" in {
        test(new FixedCounter(8)) { c =>
            c.clock.step(256)
            c.io.out.expect(0.U)
        }
    }
}