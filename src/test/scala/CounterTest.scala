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

    "A counter" should "count up" in {
        test(new MyCounter(8)) { c =>
            c.io.out.expect(0.U)
            c.clock.step()
            c.io.out.expect(1.U)
        }
    }
}