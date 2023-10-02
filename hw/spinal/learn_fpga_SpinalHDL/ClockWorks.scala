package learn_fpga_SpinalHDL

import spinal.core._

case class ClockWorks (SLOW:Int=21, BENCH:Boolean = false, NEGATIVE_RESET:Boolean = true) extends Component{
  val io = new Bundle{
    val CLK = in Bool()
    val RESET = in Bool()
    val clk = out Bool()
    val rst_n = out Bool()
  }
  if(NEGATIVE_RESET){
    io.rst_n := io.RESET
  }else{
    io.rst_n := !io.RESET
  }
  if (SLOW != 0) {
    var slow_bit: Int = SLOW
    if (BENCH && SLOW >= 4) {
      slow_bit = slow_bit - 4
    }
    val CLKD = new ClockDomain(io.CLK,  if(NEGATIVE_RESET){io.RESET}else{!io.RESET})
    val CLKA = new ClockingArea(CLKD){
      val slow_CLK = Reg(UInt(slow_bit + 1 bits)) init 0
      slow_CLK := slow_CLK + 1
      io.clk := slow_CLK(slow_bit)
    }
  }
}

object ClockWorks extends App {
  Config.spinal.generateVerilog(ClockWorks())
}