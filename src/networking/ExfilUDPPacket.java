package networking;

public class ExfilUDPPacket implements Comparable{
	private byte[] data; 
	private int seqNum;
	private int seqPos;
	public ExfilUDPPacket(byte[] data, int seqPos, int seqNum){
		this.data=data;
		this.seqNum=seqNum;
		this.seqPos=seqPos;
	}
	public byte[] getData(){
		return this.data;
	}
	public int getSeqNum(){
		return this.seqNum;
	}
	public int getSeqPos() {
		return seqPos;
	}
	@Override
	public int compareTo(Object o) {
		ExfilUDPPacket input =(ExfilUDPPacket)o;
		int inputSeqNum = input.getSeqNum();
		int inputSeqPos = input.getSeqPos();
		if(inputSeqNum > this.getSeqNum()){
			return 1;
		}else if(inputSeqNum < this.getSeqNum()){
			return -1;
		}else if(inputSeqPos > this.getSeqPos()){
			return 1;
		}else if(inputSeqNum < this.getSeqPos()){
			return -1;
		}
		return 0;
	}
	
}
