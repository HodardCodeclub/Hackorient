package rw.hackorient.dequeue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Stop implements Parcelable {

	@SerializedName("modes")
	private List<String> modes;

	@SerializedName("agency")
	private Agency agency;

	@SerializedName("name")
	private String name;

	@SerializedName("geometry")
	private Geometry geometry;

	@SerializedName("id")
	private String id;

	@SerializedName("href")
	private String href;

	public void setModes(List<String> modes){
		this.modes = modes;
	}

	public List<String> getModes(){
		return modes;
	}

	public void setAgency(Agency agency){
		this.agency = agency;
	}

	public Agency getAgency(){
		return agency;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setHref(String href){
		this.href = href;
	}

	public String getHref(){
		return href;
	}

	@Override
 	public String toString(){
		return 
			"Stop{" + 
			"modes = '" + modes + '\'' + 
			",agency = '" + agency + '\'' + 
			",name = '" + name + '\'' + 
			",geometry = '" + geometry + '\'' + 
			",id = '" + id + '\'' + 
			",href = '" + href + '\'' + 
			"}";
		}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringList(this.modes);
		dest.writeParcelable(this.agency, flags);
		dest.writeString(this.name);
		dest.writeParcelable(this.geometry, flags);
		dest.writeString(this.id);
		dest.writeString(this.href);
	}

	public Stop() {
	}

	protected Stop(Parcel in) {
		this.modes = in.createStringArrayList();
		this.agency = in.readParcelable(Agency.class.getClassLoader());
		this.name = in.readString();
		this.geometry = in.readParcelable(Geometry.class.getClassLoader());
		this.id = in.readString();
		this.href = in.readString();
	}

	public static final Parcelable.Creator<Stop> CREATOR = new Parcelable.Creator<Stop>() {
		@Override
		public Stop createFromParcel(Parcel source) {
			return new Stop(source);
		}

		@Override
		public Stop[] newArray(int size) {
			return new Stop[size];
		}
	};
}