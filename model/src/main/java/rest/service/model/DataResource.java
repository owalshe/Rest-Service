package rest.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DataResource {

	@Id
	@GeneratedValue
	private Long id;
	private String data1;
	private String data2;
	private String data3;
	
	public DataResource(String data1, String data2, String data3) {
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
	}
	
	public DataResource() {}
	
	public String getData1() {
		return this.data1;
	}
	
	public String getData2() {
		return this.data2;
	}
	
	public String getData3() {
		return this.data3;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %s, %s, %s]", this.id, this.data1, this.data2, this.data3);
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}
}
