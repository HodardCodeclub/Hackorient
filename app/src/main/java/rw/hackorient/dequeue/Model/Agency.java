package rw.hackorient.dequeue.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Agency implements Parcelable {

	@SerializedName("culture")
	private String culture;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("href")
	private String href;

	public void setCulture(String culture){
		this.culture = culture;
	}

	public String getCulture(){
		return culture;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"Agency{" +
			"culture = '" + culture + '\'' + 
			",name = '" + name + '\'' + 
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
		dest.writeString(this.culture);
		dest.writeString(this.name);
		dest.writeString(this.id);
		dest.writeString(this.href);
	}

	public Agency() {
	}

	protected Agency(Parcel in) {
		this.culture = in.readString();
		this.name = in.readString();
		this.id = in.readString();
		this.href = in.readString();
	}

	public static final Parcelable.Creator<Agency> CREATOR = new Parcelable.Creator<Agency>() {
		@Override
		public Agency createFromParcel(Parcel source) {
			return new Agency(source);
		}

		@Override
		public Agency[] newArray(int size) {
			return new Agency[size];
		}
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Agency agency = (Agency) o;

		return id.equals(agency.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}