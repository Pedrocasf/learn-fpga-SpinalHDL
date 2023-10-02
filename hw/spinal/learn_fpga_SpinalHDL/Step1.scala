package learn_fpga_SpinalHDL

import spinal.core._

case class Step1() extends Component{
  val io = new Bundle{
    val leds = out Bits(5 bits)
    val rxd = in Bool()
    val txd = out Bool()
  }
  val count = Reg(UInt(5 bits)) init 0
  count := count + 1
  io.leds := count.asBits
  io.txd := io.rxd
}
object Step1 extends App {
  Config.spinal.generateVerilog(Step1())
}