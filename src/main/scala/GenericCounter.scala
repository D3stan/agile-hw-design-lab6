import chisel3._

class GenericCounter(n : Int) extends Module with HasCounterOut {

    def minX(y: Int): Int =
        if (y <= 0) 0
        else 32 - java.lang.Integer.numberOfLeadingZeros(y)

    val n_width = minX(n)

    val io = IO(new Bundle {
        val out = Output(UInt(n_width.W))
    })

    val reg = RegInit(0.U(n_width.W))
    reg := Mux(reg === n.U, 0.U, reg + 1.U)

    io.out := reg
    override def out: UInt = io.out
}