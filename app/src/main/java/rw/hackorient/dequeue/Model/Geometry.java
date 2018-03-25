package rw.hackorient.dequeue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Geometry implements Parcelable {

	@SerializedName("coordinates")
	private List<Double> coordinates;

	@SerializedName("type")
	private String type;

	public void setCoordinates(List<Double> coordinates){
		this.coordinates = coordinates;
	}

	public List<Double> getCoordinates(){
		return coordinates;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"Geometry{" + 
			"coordinates = '" + coordinates + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(this.coordinates);
		dest.writeString(this.type);
	}

	public Geometry() {
	}

	protected Geometry(Parcel in) {
		this.coordinates = new ArrayList<Double>();
		in.readList(this.coordinates, Double.class.getClassLoader());
		this.type = in.readString();
	}

	public static final Parcelable.Creator<Geometry> CREATOR = new Parcelable.Creator<Geometry>() {
		@Override
		public Geometry createFromParcel(Parcel source) {
			return new Geometry(source);
		}

		@Override
		public Geometry[] newArray(int size) {
			return new Geometry[size];
		}
	};
}