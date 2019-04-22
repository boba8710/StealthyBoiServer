package networking;

public class ExfilUDPPacket implements Comparable<ExfilUDPPacket>{
	private byte[] data; 
	private int seqNum;
	private int seqPos;
	private int identifier = 0;
	public ExfilUDPPacket(byte[] data, int identifier){
		this.data=data;
		this.identifier=identifier;
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
	public int getIdentifier(){
		return identifier;
	}
	@Override
	public int compareTo(ExfilUDPPacket arg0) {
		if(arg0.getIdentifier() > this.getIdentifier()){
			return -1;
		}else if(arg0.getIdentifier() < this.getIdentifier()){
			return 1;
		}else{
			return 0;
		}
	}
	
}
