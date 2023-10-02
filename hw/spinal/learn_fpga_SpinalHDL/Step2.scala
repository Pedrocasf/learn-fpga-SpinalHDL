package learn_fpga_SpinalHDL

import spinal.core._

case class Step2() extends Component{
  val io = new Bundle{
    val CLK = in Bool()
    val RESET = in Bool()
    val leds = out Bits(5 bits)
  }
  val CLK = Reg(Bool())
  CLK := io.CLK
  val RESET = Reg(Bool())
  RESET := io.RESET
  val CW = ClockWorks(21 ,true, false)
  CW.io.CLK := CLK
  CW.io.RESET := RESET
  val CLKD = new ClockDomain(CW.io.clk,CW.io.rst_n)
  val CLKA = new ClockingArea(CLKD){
    val count = Reg(UInt(5 bits)) init 0
    count := count + 1
  }
  io.leds := CLKA.count.asBits
}
object Step2 extends App {
  Config.spinal.generateVerilog(Step2())
}