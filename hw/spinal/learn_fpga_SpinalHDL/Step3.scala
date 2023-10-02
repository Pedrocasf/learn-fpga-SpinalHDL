package learn_fpga_SpinalHDL

import spinal.core._

case class Step3 () extends Component{
  val io = new Bundle{
    val CLK = in Bool()
    val RESET = in Bool()
    val LEDS = out Bits (5 bits)
  }
  val CLK = Reg(Bool())
  CLK := io.CLK
  val RESET = Reg(Bool())
  RESET := io.RESET
  val CW = new ClockWorks(21, true, false)
  CW.io.CLK := CLK
  CW.io.RESET := RESET
  val CLKD = new ClockDomain(CW.io.clk, CW.io.rst_n)
  val CLKA = new ClockingArea(CLKD) {
    val pc = Reg(UInt(5 bits)) init 0
    val mem = Mem(Bits(5 bits), 21) init Seq(0, 1, 2, 4, 8, 16, 17, 18, 20, 24, 25, 26, 28, 29, 30, 31, 30, 28, 24, 16, 0)
    val leds = Reg(Bits(5 bits)) init 0
    leds := mem.readSync(pc)
    pc := Mux(pc === U(20), U(0), pc+1)
  }
  io.LEDS := CLKA.leds
}
object Step3 extends App {
  Config.spinal.generateVerilog(Step3())
}
