package com.assusoft.tmxloader.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.lang.Math;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.epch.efair.delhifair.HomeAcitityFirst;
import com.epch.efair.delhifair.ImageAsyncTask;
import com.epch.efair.delhifair.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

/**
 * Loader for .tmx XML map file
 */
public class TMXLoader {
	
	/**
	 * Create a bitmap based on the data in the TileMapData structure.
	 * 
	 * @param	t 	data structure describing the TileMap
	 * @param	c 	application context
	 * @param	startLayer	index of the first layer to render
	 * @param	endLayer	index of the last layer to render
	 * @return		bitmap of the map 
	 */
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int maxMemory) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

//    if (height > reqHeight || width > reqWidth) {
//
//        final int halfHeight = height / 2;
//        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((width / inSampleSize) * (height / inSampleSize) * 4 > maxMemory) {
            inSampleSize *= 2;
        }
   // }

    return inSampleSize;
}
//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//            int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }

	@SuppressLint("NewApi")
	public static Bitmap createBitmap(TileMapData t, Context c, int startLayer, int endLayer, int x, int y){
	    	
	    	
	    	
	    	try{
	    		AssetManager assetManager = c.getAssets();
	    		
	    		// Create a bitmap of the size of the map.
	    		// Straight up creating a bitmap of arbitrary size is huge in memory, but this is
	    		// sufficient for small demo purposes.
	    		
	    		// In a production engine, map size should either be restricted,
	    		// or the map should be loaded to memory on the fly.
	    		Bitmap mapImage = Bitmap.createBitmap(t.width*t.tilewidth,t.height*t.tileheight, Bitmap.Config.ARGB_4444);
	    		
	    		// Load all tilesets that are used into memory. Again, not
	    		// very efficient, but loading the image, dereferencing, and running
	    		// the gc for each image is not a fast or good option.
	    		// Still, a better way is forthcoming if I can think of one.
	    		Bitmap[] tilesets = new Bitmap[t.tilesets.size()];
	    		
	    		for (int i = 0; i < tilesets.length; i++){
	    			Log.i("EXPO", "[TMX] "+t.tilesets.get(i).ImageFilename);
	    			/**From Asset Folder**/
	    			//tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename));
	    			
	    			/** From SD Cards **/
	    			tilesets[i] = BitmapFactory.decodeStream(new FileInputStream(
	    					new File(Environment.getExternalStorageDirectory().getAbsoluteFile()
	    							+File.separator+ImageAsyncTask.FOLDER_NAME
	    							+File.separator+t.tilesets.get(i).ImageFilename)));
	    			
//	    			final BitmapFactory.Options opts = new BitmapFactory.Options();
//	    	        opts.inJustDecodeBounds = true;
//	    	        opts.inPurgeable=true;
//	    	       
//	    	        Rect out = null;
//					BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
//
//					long maxMemory = Runtime.getRuntime().maxMemory();
//			        int maxMemoryForImage = (int) (maxMemory / 100 * 25);
//	    	        // Calculate inSampleSize
//	    	        opts.inSampleSize = calculateInSampleSize(opts, maxMemoryForImage);
//
//	    	        // Decode bitmap with inSampleSize set
//	    	        opts.inJustDecodeBounds = false;
//	    	        tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
	    		 
	    		}
	    		
	    		// Create a Canvas reference to our map Bitmap
	    		// so that we can blit to it.
	    		
	    		Canvas mapCanvas = new Canvas(mapImage);
	    		
	    		
	    		// Loop through all layers and x and y-positions
	    		// to render all the tiles.
	    		
	    		// Later I'll add in an option for specifying which layers
	    		// to display, in case some hold invisible or meta-tiles.
	    		
	    		long currentGID;
	    		Long localGID;
	    		Integer currentTileSetIndex;
	    		Rect source = new Rect(0, 0, 0, 0);
	    		Rect dest = new Rect(0, 0, 0, 0);
	    		
	    		//y-=25;x-=15;
	    			    		
	    		for (int i = startLayer; i < endLayer; i++){
	    		
//	    			if(y+25>t.layers.get(i).height)
//	    				y=y+y+25-t.layers.get(i).height;
//	    			    				
//	    			if(x+15>t.layers.get(i).width)
//	    				x=x+x+15-t.layers.get(i).width;
	    			for(int j = Math.max(y, 0),l=0; j < Math.min(250, t.layers.get(i).height); j++,l++){
	    				
	    				for (int k = Math.max(x, 0),m=0; k < Math.min(150, t.layers.get(i).width); k++,m++){
	    					
	    					currentGID = t.getGIDAt(k, j, i);
	    					localGID = t.getLocalID(currentGID);
	    					// debug
	    					//if (localGID == null) Log.d("GID", "Read problem");
	    					//Log.d("Tilegid", String.valueOf(localGID));
	    					currentTileSetIndex  = t.getTileSetIndex(currentGID);
	    					
	    					// The row number is the number of tiles wide the image is divided by
	    					// the tile number
	    					
	    					// Check that this space isn't buggy or undefined, and 
	    					// if everything's fine, blit to the current x, y position
	    					if (localGID != null){
		    					source.top = (((localGID).intValue())/((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tileheight; 
		    					source.left = (((localGID).intValue())%((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tilewidth;
		    					source.bottom = source.top + t.tileheight;
		    					source.right = source.left + t.tilewidth;
		    					
		    					
		    					dest.top = l*t.tileheight;
		    					dest.left = m*t.tilewidth;
		    					dest.bottom = dest.top + t.tileheight;
		    					dest.right = dest.left + t.tilewidth;
		    					
		    					
		    					mapCanvas.drawBitmap(tilesets[currentTileSetIndex], source, dest, new Paint());				
	    					}
	    					
	    				}
	    				
	    			}    			    			
	    			
	    		}
	    		
	    		
	    		
	    		return mapImage;
	    		
	    	}
	    	catch(OutOfMemoryError e){
	    		e.printStackTrace();
	    	}
	    	catch (Exception e){
	    		// In case the tilemap files are missing
	    		Log.d("IOException", e.toString());
	    		HomeAcitityFirst.lowMemoryIndicator=1;
	    	}
	    	
	    	// In case the image didn't load properly
	    	return null;
	    }


	/**
	 * Reads XML file from assets and returns a TileMapData structure describing its contents
	 * 
	 * @param	filename	path to the file in assets
	 * @param	c			context of the application to resolve assets folder
	 * @return				data structure containing map data
	 */
	public static TileMapData readTMX(String filename, Context c){
		TileMapData t = null;
		
		// Initialize SAX
		try{
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			
			// Create an instance of the TMX XML handler
			// that will create a TileMapData object
			TMXHandler handler = new TMXHandler();
			
			reader.setContentHandler(handler);
			
			//AssetManager assetManager = c.getAssets();
			
			/** * Reading tmx file from sd card. * */
			File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile().toString()
					+File.separator+ImageAsyncTask.FOLDER_NAME+File.separator+filename);
			reader.parse((new InputSource(new FileInputStream(file))));
			
			/**From Asset Folder**/
			//reader.parse((new InputSource(assetManager.open(filename))));
			
			// Extract the created object
			t = handler.getData();
			Log.i("EXPO","[t] "+t);
			
		} catch(ParserConfigurationException pce) { 
		    Log.e("SAX XML", "sax parse error", pce); 
		} catch(SAXException se) { 
		    Log.e("SAX XML", "sax error", se); 
		} catch(IOException ioe) { 
		    Log.e("SAX XML", "sax parse io error", ioe); 
		}finally{
			
		}
		
		
		return t;
	}
	
    public static long[][] convertToArray(TileMapData t)
    {
    	long[][] m= new long[t.layers.get(0).height][t.layers.get(0).width];
    	long currentGID;
    	
    	for(int j=0;j<t.layers.get(0).height;j++)
    		for(int k=0;k<t.layers.get(0).width;k++)
    		{
    			currentGID = t.getGIDAt(k, j,0);
    			m[j][k]=currentGID;
    		}
    	return m;
    }
    
    public static long[][] convertToArray(TileMapData t,int tileLayer)
    {
    	long[][] m= new long[t.layers.get(tileLayer).height][t.layers.get(tileLayer).width];
    	long currentGID;
    	
    	for(int j=0;j<t.layers.get(tileLayer).height;j++)
    		for(int k=0;k<t.layers.get(tileLayer).width;k++)
    		{
    			currentGID = t.getGIDAt(k, j,tileLayer);
    			m[j][k]=currentGID;
    		}
    	return m;
    }
	
    public static long retTilesetFirstGID(TileMapData t,int tno)
    {
    	 return t.tilesets.get(tno).firstGID;
    }
    
    public static int retLayerHeight(TileMapData t)
    {
    	return t.layers.get(0).height;
    }
    
    public static int retLayerWidth(TileMapData t)
    {
    	return t.layers.get(0).width;
    }
    
    public static int noOfTilesets(TileMapData t)
    {
    	return t.tilesets.size();
    }
    
	public static Bitmap createBitmap(TileMapData t, Context c, int startLayer, int endLayer, long[][] m){
		
		try{
    		AssetManager assetManager = c.getAssets();
    		
    		// Create a bitmap of the size of the map.
    		// Straight up creating a bitmap of arbitrary size is huge in memory, but this is
    		// sufficient for small demo purposes.
    		
    		// In a production engine, map size should either be restricted,
    		// or the map should be loaded to memory on the fly.
    		//int width=Math.min(maxy+10,t.layers.get(0).width)-Math.max(miny-10, 0);
    		//int height=Math.max(Math.min(maxx+10,t.layers.get(0).height)-Math.max(minx-10, 0),width);
    		Bitmap mapImage = Bitmap.createBitmap(t.width*t.tilewidth,t.height*t.tileheight, Bitmap.Config.ARGB_4444);
    		//System.out.println("Bitmap:"+height+" "+width);
    		// Load all tilesets that are used into memory. Again, not
    		// very efficient, but loading the image, dereferencing, and running
    		// the gc for each image is not a fast or good option.
    		// Still, a better way is forthcoming if I can think of one.
    		Bitmap[] tilesets = new Bitmap[t.tilesets.size()];
    		
    		for (int i = 0; i < tilesets.length; i++){
    			tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename));
//    			final BitmapFactory.Options opts = new BitmapFactory.Options();
//    	        opts.inJustDecodeBounds = true;
//    	        opts.inPurgeable=true;
//    	       
//    	        Rect out = null;
//				BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
//
//				long maxMemory = Runtime.getRuntime().maxMemory();
//		        int maxMemoryForImage = (int) (maxMemory / 100 * 25);
//    	        // Calculate inSampleSize
//    	        opts.inSampleSize = calculateInSampleSize(opts, maxMemoryForImage);
//
//    	        // Decode bitmap with inSampleSize set
//    	        opts.inJustDecodeBounds = false;
//    	        tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
    		 
    		}
    		
    		// Create a Canvas reference to our map Bitmap
    		// so that we can blit to it.
    		
    		Canvas mapCanvas = new Canvas(mapImage);
    		
    		
    		// Loop through all layers and x and y-positions
    		// to render all the tiles.
    		
    		// Later I'll add in an option for specifying which layers
    		// to display, in case some hold invisible or meta-tiles.
    		
    		long currentGID;
    		Long localGID;
    		Integer currentTileSetIndex;
    		Rect source = new Rect(0, 0, 0, 0);
    		Rect dest = new Rect(0, 0, 0, 0);
    		
    		/*Bitmap markerFrom=BitmapFactory.decodeResource(c.getResources(),R.drawable.from_pin);
    		Bitmap markerTo=BitmapFactory.decodeResource(c.getResources(),R.drawable.to_pin);
    		*/
    		//y-=25;x-=15;
    			    		
    		for (int i = startLayer; i < endLayer; i++){
    		
//    			if(y+25>t.layers.get(i).height)
//    				y=y+y+25-t.layers.get(i).height;
//    			    				
//    			if(x+15>t.layers.get(i).width)
//    				x=x+x+15-t.layers.get(i).width;
    			for(int j = 0,l=0; j < t.layers.get(i).height; j++,l++){
    				
    				for (int k = 0,n=0; k < t.layers.get(i).width; k++,n++){
    					
    					currentGID = t.getGIDAt(k, j, i);
    					localGID = t.getLocalID(currentGID);
    					// debug
    					//if (localGID == null) Log.d("GID", "Read problem");
    					//Log.d("Tilegid", String.valueOf(localGID));
    					currentTileSetIndex  = t.getTileSetIndex(currentGID);
    					
    					// The row number is the number of tiles wide the image is divided by
    					// the tile number
    					
    					// Check that this space isn't buggy or undefined, and 
    					// if everything's fine, blit to the current x, y position
    					if (localGID != null){
	    					source.top = (((localGID).intValue())/((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tileheight; 
	    					source.left = (((localGID).intValue())%((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tilewidth;
	    					source.bottom = source.top + t.tileheight;
	    					source.right = source.left + t.tilewidth;
	    					
	    					
	    					dest.top = l*t.tileheight;
	    					dest.left = n*t.tilewidth;
	    					dest.bottom = dest.top + t.tileheight;
	    					dest.right = dest.left + t.tilewidth;
	    					
	    					
	    					mapCanvas.drawBitmap(tilesets[currentTileSetIndex], source, dest, new Paint());				
    					}
    					
    				}
    				
    			}    			    			
    			
    		}
    		
    		for(int j = 0,l=0; j < t.layers.get(endLayer).height; j++,l++){
				
				for (int k = 0,n=0; k <t.layers.get(endLayer).width; k++,n++){
					
					currentGID = t.getGIDAt(k, j);
					localGID = t.getLocalID(currentGID);
					// debug
					//if (localGID == null) Log.d("GID", "Read problem");
					//Log.d("Tilegid", String.valueOf(localGID));
					currentTileSetIndex  = t.getTileSetIndex(currentGID);
					
					// The row number is the number of tiles wide the image is divided by
					// the tile number
					
					// Check that this space isn't buggy or undefined, and 
					// if everything's fine, blit to the current x, y position
					if (m[j][k]!=0){
//    					source.top = (((localGID).intValue())/((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tileheight; 
//    					source.left = (((localGID).intValue())%((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tilewidth;
//    					source.bottom = source.top + t.tileheight;
//    					source.right = source.left + t.tilewidth;
    					
    					
    					dest.top = l*t.tileheight;
    					dest.left = n*t.tilewidth;
    					dest.bottom = dest.top + t.tileheight;
    					dest.right = dest.left + t.tilewidth;
    					
    					Paint pc= new Paint();
    					if(m[j][k]==1){
    						pc.setColor(Color.YELLOW);
    					}
    					else if(m[j][k]==2){
    						pc.setColor(0xf400ff00);
    						//mapCanvas.drawBitmap(markerFrom,dest.left-27,dest.top-56, null);
    					}
    					else if(m[j][k]==3){
    						pc.setColor(0xf4ff0000);
    						//mapCanvas.drawBitmap(markerTo,dest.left-27,dest.top-56, null);
    						
    					}
    					mapCanvas.drawRect(dest,pc);				
					}
					
				}
				
			}    			    			
    		
    		return mapImage;
    		
    	}
    	catch (IOException e){
    		// In case the tilemap files are missing
    		Log.d("IOException", e.toString());
    	}
    	
    	// In case the image didn't load properly
    	return null;
	}
	
public static Bitmap createBitmap(TileMapData t, Context c, int startLayer, int endLayer, long[][] m, int minx, int miny, int maxx, int maxy){
		
		try{
    		AssetManager assetManager = c.getAssets();
    		
    		// Create a bitmap of the size of the map.
    		// Straight up creating a bitmap of arbitrary size is huge in memory, but this is
    		// sufficient for small demo purposes.
    		
    		// In a production engine, map size should either be restricted,
    		// or the map should be loaded to memory on the fly.
    		int width=Math.min(maxy+10,t.layers.get(0).width)-Math.max(miny-10, 0);
    		int height=Math.max(Math.min(maxx+10,t.layers.get(0).height)-Math.max(minx-10, 0),width);
    		Bitmap mapImage = Bitmap.createBitmap(t.width*t.tilewidth,t.height*t.tileheight, Bitmap.Config.ARGB_4444);
    		System.out.println("Bitmap:"+height+" "+width);
    		// Load all tilesets that are used into memory. Again, not
    		// very efficient, but loading the image, dereferencing, and running
    		// the gc for each image is not a fast or good option.
    		// Still, a better way is forthcoming if I can think of one.
    		Bitmap[] tilesets = new Bitmap[t.tilesets.size()];
    		
    		for (int i = 0; i < tilesets.length; i++){
    			tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename));
//    			final BitmapFactory.Options opts = new BitmapFactory.Options();
//    	        opts.inJustDecodeBounds = true;
//    	        opts.inPurgeable=true;
//    	       
//    	        Rect out = null;
//				BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
//
//				long maxMemory = Runtime.getRuntime().maxMemory();
//		        int maxMemoryForImage = (int) (maxMemory / 100 * 25);
//    	        // Calculate inSampleSize
//    	        opts.inSampleSize = calculateInSampleSize(opts, maxMemoryForImage);
//
//    	        // Decode bitmap with inSampleSize set
//    	        opts.inJustDecodeBounds = false;
//    	        tilesets[i] = BitmapFactory.decodeStream(assetManager.open(t.tilesets.get(i).ImageFilename), out, opts);
    		 
    		}
    		
    		// Create a Canvas reference to our map Bitmap
    		// so that we can blit to it.
    		
    		Canvas mapCanvas = new Canvas(mapImage);
    		
    		
    		// Loop through all layers and x and y-positions
    		// to render all the tiles.
    		
    		// Later I'll add in an option for specifying which layers
    		// to display, in case some hold invisible or meta-tiles.
    		
    		long currentGID;
    		Long localGID;
    		Integer currentTileSetIndex;
    		Rect source = new Rect(0, 0, 0, 0);
    		Rect dest = new Rect(0, 0, 0, 0);
    		
    		//y-=25;x-=15;
    			    		
    		for (int i = startLayer; i < endLayer; i++){
    		
//    			if(y+25>t.layers.get(i).height)
//    				y=y+y+25-t.layers.get(i).height;
//    			    				
//    			if(x+15>t.layers.get(i).width)
//    				x=x+x+15-t.layers.get(i).width;
    			for(int j = Math.max(minx-10, 0),l=0; j < Math.min(maxx+10,t.layers.get(i).height); j++,l++){
    				
    				for (int k = Math.max(miny-10, 0),n=0; k < Math.min(maxy+10,t.layers.get(i).width); k++,n++){
    					
    					currentGID = t.getGIDAt(k, j, i);
    					localGID = t.getLocalID(currentGID);
    					// debug
    					//if (localGID == null) Log.d("GID", "Read problem");
    					//Log.d("Tilegid", String.valueOf(localGID));
    					currentTileSetIndex  = t.getTileSetIndex(currentGID);
    					
    					// The row number is the number of tiles wide the image is divided by
    					// the tile number
    					
    					// Check that this space isn't buggy or undefined, and 
    					// if everything's fine, blit to the current x, y position
    					if (localGID != null){
	    					source.top = (((localGID).intValue())/((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tileheight; 
	    					source.left = (((localGID).intValue())%((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tilewidth;
	    					source.bottom = source.top + t.tileheight;
	    					source.right = source.left + t.tilewidth;
	    					
	    					
	    					dest.top = l*t.tileheight;
	    					dest.left = n*t.tilewidth;
	    					dest.bottom = dest.top + t.tileheight;
	    					dest.right = dest.left + t.tilewidth;
	    					
	    					
	    					mapCanvas.drawBitmap(tilesets[currentTileSetIndex], source, dest, new Paint());				
    					}
    					
    				}
    				
    			}    			    			
    			
    		}
    		
    		for(int j = Math.max(minx-10, 0),l=0; j < Math.min(maxx+10,t.layers.get(0).height); j++,l++){
				
				for (int k = Math.max(miny-10, 0),n=0; k < Math.min(maxy+10,t.layers.get(0).width); k++,n++){
					
					currentGID = t.getGIDAt(k, j);
					localGID = t.getLocalID(currentGID);
					// debug
					//if (localGID == null) Log.d("GID", "Read problem");
					//Log.d("Tilegid", String.valueOf(localGID));
					currentTileSetIndex  = t.getTileSetIndex(currentGID);
					
					// The row number is the number of tiles wide the image is divided by
					// the tile number
					
					// Check that this space isn't buggy or undefined, and 
					// if everything's fine, blit to the current x, y position
					if (m[j][k]!=0){
//    					source.top = (((localGID).intValue())/((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tileheight; 
//    					source.left = (((localGID).intValue())%((t.tilesets.get(currentTileSetIndex).imageWidth)/t.tilewidth))*t.tilewidth;
//    					source.bottom = source.top + t.tileheight;
//    					source.right = source.left + t.tilewidth;
    					
    					
    					dest.top = l*t.tileheight;
    					dest.left = n*t.tilewidth;
    					dest.bottom = dest.top + t.tileheight;
    					dest.right = dest.left + t.tilewidth;
    					
    					Paint pc= new Paint();
    					if(m[j][k]==1)
    						pc.setColor(0x640000ff);
    					else if(m[j][k]==2)
    						pc.setColor(0xf4ff0000);
    					else if(m[j][k]==3)
    						pc.setColor(0xf400ff00);
    					mapCanvas.drawRect(dest,pc);				
					}
					
				}
				
			}    			    			
    		
    		return mapImage;
    		
    	}
    	catch (IOException e){
    		// In case the tilemap files are missing
    		Log.d("IOException", e.toString());
    	}
    	
    	// In case the image didn't load properly
    	return null;
	}
}
