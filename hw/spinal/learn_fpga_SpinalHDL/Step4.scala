package learn_fpga_SpinalHDL

import spinal.core._

case class Step4() extends Component{
  val io = new Bundle{
    val RXD = in Bool()
    val LEDS = out Bits(5 bits)
    val TXD = out Bool()
  }
  io.TXD := False
  val mem = Mem(Bits(32 bits), 8) init Seq(B"b0000000_00000_00000_000_00001_0110011",B"b000000000001_00001_000_00001_0010011",B"b000000000001_00001_000_00001_0010011",B"b000000000001_00001_000_00001_0010011",B"b000000000001_00001_000_00001_0010011",B"b000000000000_00001_010_00010_0000011",B"b000000_00010_00001_010_00000_0100011",B"b000000000001_00000_000_00000_1110011")
  val pc = Reg(UInt(32 bits)) init 0
  val instr = Reg(Bits(32 bits)) init B"b00000000000000000000000000110011"
  val isALUreg  = instr(6 downto 0) === B"b0110011"
  val isALUimm = instr(6 downto 0 ) === B"b0010011"
  val isBranch = instr(6 downto 0 ) === B"b1100011"
  val isJALR = instr(6 downto 0 ) === B"b1100111"
  val isJAL = instr(6 downto 0 ) === B"b1101111"
  val isAUIPC = instr(6 downto 0 ) === B"b0010111"
  val isLUI = instr(6 downto 0 ) === B"b0110111"
  val isLoad = instr(6 downto 0 ) === B"0000011"
  val isStore = instr(6 downto 0 ) === B"b0100011"
  val isSYSTEM = instr(6 downto 0 ) === B"b1110011"
  val Uimm = instr(32) ## instr(30 downto 12) ## B"b000000000000"
  val Iimm = B(21 bits, default -> instr(31)) ## instr(30 downto 20)
  val Simm = B(21 bits, default -> instr(31)) ## instr(30 downto 25) ## instr(11 downto 7)
  val Bimm = B(20 bits, default -> instr(31)) ## instr(7) ## instr(30 downto 25) ## instr(11 downto 8) ## B"b0"
  val Jimm = B(21 bits, default -> instr(31)) ## instr(19 downto 12) ## instr(20) ## instr(30 downto 21) ## B"b0"
  val rs1Id = instr(19 downto 15)
  val rs2Id = instr(24 downto 20)
  val rdId = instr(11 downto 7)
  val funct3 = instr(14 downto 12)
  val funct7 = instr(31 downto 25)
  when(!isSYSTEM) {
    instr := mem.readSync(pc(2 downto 0))
    pc := pc + U(1)
  }
  io.LEDS :=isSYSTEM ? B"b11111" | pc(0) ## isALUreg ## isALUimm ## isStore ## isLoad
}
object Step4 extends App {
  Config.spinal.generateVerilog(Step4())
}