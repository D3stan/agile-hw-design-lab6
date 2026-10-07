import chisel3._

class FixedCounter(width: Int) extends Module {
    val io = IO(new Bundle {
        val out = Output(UInt(width.W))
    })

    val count = RegInit(0.U(width.W))
    count := count + 1.U
    io.out := count
}