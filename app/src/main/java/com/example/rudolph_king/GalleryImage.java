package com.example.rudolph_king;

import android.graphics.Bitmap;
import android.net.Uri;
import java.util.ArrayList;

public class GalleryImage {
    private ArrayList<Uri> uriList;

    public void setUriList(ArrayList<Uri> uri) { this.uriList = uri ;}
    public ArrayList<Uri> getUriList() {return this.uriList;}
}
