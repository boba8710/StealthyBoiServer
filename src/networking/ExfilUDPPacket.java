package networking;

public class ExfilUDPPacket {
	byte[] data; 
	int seqNum;
	public ExfilUDPPacket(byte[] data, int seqNum){
		this.data=data;
		this.seqNum=seqNum;
	}
	public byte[] getData(){
		return this.data;
	}
	public int getSeqNum(){
		return this.seqNum;
	}
}
