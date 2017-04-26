package com.assusoft.tmxloader.library;
/*    
 * A* algorithm implementation.
 * Copyright (C) 2007, 2009 Giuseppe Scrivano <gscrivano@gnu.org>

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
                        
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.Log;

import com.assusoft.eFairEmall.entities.FacilityInformation;
import com.assusoft.efair.epchfair.Fragments.VenueMapRouteTwo;
import com.assusoft.efair.epchfair.Fragments.VenueMapWithRoute;


/*
 * Example.
 */
public class PathFinder extends AStar
{
                
				private long threshold, marker, gate;
				int routeTileValue;
                
                public PathFinder(long[][] map){
                				super(map);
                                //this.map = map;
                }

                protected boolean isGoal(Node node, ArrayList<Node> end){
                				for(Node t: end)
                				{
                					if((node.x == t.x) && (node.y == t.y))
                						return true;
                				}
                                return false;
                }

                protected Double g(Node from, Node to){

                                if(from.x == to.x && from.y == to.y)
                                                return 0.0;

                               /* if(map[to.y][to.x] < threshold && map[to.y][to.x]!=0)
                                                return 1.0;*/
                                if( map[to.y][to.x]!=0)
                                    return 1.0;

                                return Double.MAX_VALUE;
                }

                @SuppressLint("UseValueOf")
				protected Double h(ArrayList<Node> end, Node to){
                                /* Use the Manhattan distance heuristic.  */
                				Double m= Double.MAX_VALUE;
			                	for(Node t: end)
			    				{
			                		m=(double)Math.min(m, (Math.abs(t.x - to.x) + Math.abs(t.y - to.y)));
			                		
			    				}
			                	
                                return m*(1+0.001); 
                }

                protected List<Node> generateSuccessors(Node node){
                                /*List<Node> ret = new LinkedList<Node>();
                                int x = node.x;
                                int y = node.y;
                                if(y < map.length - 1 && map[y+1][x] < threshold && map[y+1][x]!=0)
                                                ret.add(new Node(x, y+1));

                                if(x < map[0].length - 1 && map[y][x+1] < threshold && map[y][x+1]!=0)
                                                ret.add(new Node(x+1, y));
                                if(y>0 && map[y-1][x] < threshold && map[y-1][x]!=0)
                                	ret.add(new Node(x,y-1));
                                if(x>0 && map[y][x-1] < threshold && map[y][x-1]!=0)
                                	ret.add(new Node(x-1,y));*/
                	
                	List<Node> ret = new LinkedList<Node>();
                    int x = node.x;
                    int y = node.y;
                    //Log.i("EFAIR","route tile value : "+routeTileValue);
                    if(y < map.length - 1 && map[y+1][x] ==routeTileValue && map[y+1][x]!=0)
                    {
                                    ret.add(new Node(x, y+1));
                                    //Log.i("EFAIR","route tile value : below");
                    }

                    if(x < map[0].length - 1 && map[y][x+1]==routeTileValue && map[y][x+1]!=0)
                    {
                                    ret.add(new Node(x+1, y));
                                   // Log.i("EFAIR","route tile value : upper");
                    }
                    if(y>0 && map[y-1][x] ==routeTileValue && map[y-1][x]!=0){
                                   	ret.add(new Node(x,y-1));
                                   	//Log.i("EFAIR","route tile value : left");
                    }
                    if(x>0 && map[y][x-1]==routeTileValue && map[y][x-1]!=0){
                    	ret.add(new Node(x-1,y));
                    	//Log.i("EFAIR","route tile value : right");
                    }
                                
                                
                                return ret;
                }

                public long[][] pfcompute(int x1, int y1, int x2, int y2, TileMapData t,ArrayList<Integer> minmax )
                {
                	//route tile value 
                	routeTileValue=(int) map[y1][x1];
                	//First Tileset used to draw the path
                	threshold=TMXLoader.retTilesetFirstGID(t,1);
                	//Second last tileset (Red Marker) for the marker that can be clicked to obtain routes b/w buildings
                	marker=TMXLoader.retTilesetFirstGID(t,TMXLoader.noOfTilesets(t)-2);
                	//Last tileset (Blue Dot) used for entry/exit gates(can not be clicked)
                	gate=TMXLoader.retTilesetFirstGID(t,TMXLoader.noOfTilesets(t)-1);
                	Log.i("POINTS","route tile value "+routeTileValue+" threshold "+threshold +" ,marker "+marker+" ,gate "+gate);
                	int x=0,y=0;
                	int minx=Integer.MAX_VALUE;
                	int miny=Integer.MAX_VALUE;
                	int maxx=0;
                	int maxy=0;
                	long [][]m= new long[map.length][map[0].length];
                	ArrayList<Node> aln,als;
                	
                	if(map[y1][x1]>threshold && map[y1][x1]==marker)
                	{
                		System.out.println("Source is a building!");
                		HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> hm= new HashMap<PathFinder.Node,ArrayList<PathFinder.Node>>();
                		hm= VenueMapWithRoute.obtainHashMap();
                		Node node= new Node(x1,y1);
                		als = new ArrayList<Node>();
                		System.out.println(node);
                		System.out.println(hm.size());
                		if(hm.containsKey(node))
                		{
                			System.out.println("contains key");
                			als =hm.get(node);
                			for(PathFinder.Node tn: als)
                				{
                					System.out.println("Arraylist:"+ tn);
                				}
                			
                				
                		}
                		
                		//node=aln.;
                		//x2=node.x;
                		//y2=node.y;
                		else{
                			System.out.println("Marker not hashed yet!!");
                			Node n2=findGateHorizontal(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateHorizontal(node, 1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateVertical(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateVertical(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			if(hm.containsKey(node))
                    		{
                    			//System.out.println("contains key");
                    			als =hm.get(node);
                    			for(PathFinder.Node tn: als)
                    				{
                    					System.out.println("Arraylist:"+ tn);
                    				}
                    			
                    				
                    		}
                			
                			//return m;
                		}
                	}
                	else if(map[y1][x1]<threshold)//Source is a path point
                	{
                		als = new ArrayList<Node>();
                		als.add(new Node(x1,y1));
                		//als.add(new Node(0,7));
                		
                	}
                	else{//Source is a building but not a marker
                		return m;
                	}
                	
                	
                	if(map[y2][x2]>threshold && map[y2][x2]==marker)
                	{
                		System.out.println("Destination is a building!");
                		HashMap<PathFinder.Node,ArrayList<PathFinder.Node>> hm= new HashMap<PathFinder.Node,ArrayList<PathFinder.Node>>();
                		hm= VenueMapWithRoute.obtainHashMap();
                		Node node= new Node(x2,y2);
                		aln = new ArrayList<Node>();
                		System.out.println(node);
                		System.out.println(hm.size());
                		if(hm.containsKey(node))
                		{
                			System.out.println("contains key");
                			aln =hm.get(node);
                			for(PathFinder.Node tn: aln)
                				{
                					System.out.println("Arraylist:"+ tn);
                				}
                			
                				
                		}
                		
                		//node=aln.;
                		//x2=node.x;
                		//y2=node.y;
                		else{
                			System.out.println("Marker not hashed yet!!");
                			
                			Node n2=findGateHorizontal(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateHorizontal(node, 1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateVertical(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                			n2=findGateVertical(node, -1, t);
                			if(n2!=null)
                			VenueMapWithRoute.addToHashMap(node, n2);
                		  		//return m;
                			if(hm.containsKey(node))
                    		{
                    			//System.out.println("contains key");
                    			aln =hm.get(node);
                    			for(PathFinder.Node tn: aln)
                    				{
                    					System.out.println("Arraylist:"+ tn);
                    				}
                    			
                    				
                    		}
//                			aln = new ArrayList<Node>();
//                    		aln.add(new Node(6,17));
                		}
                	}
                	else if(map[y2][x2]<threshold)//Destination is a path point
                	{
                		aln = new ArrayList<Node>();
                		aln.add(new Node(x2,y2));
                		//aln.add(new Node(37,23));
                		
                	}
                	else{//Destination is a building but not a marker
                		return m;
                	}
                	
                	Log.i("POINTS",""+als+"  \nend  "+aln);
                	List<Node> nodes = compute(als,aln);
                	
                	int count=0;
                	if(nodes!=null){
                	for(Node n: nodes)
                	{
                		System.out.println("Path: "+ n);
                		if(count==0)
                		{
                			m[(int) n.y][(int) n.x]=2;
                			count++;
                		}
                		else
                			m[(int) n.y][(int) n.x]=1;
                		
                		             		
                		x=(int)n.x;
                		y=(int)n.y;
                		minx=Math.min(x, minx);
                		miny=Math.min(y, miny);
                		maxx=Math.max(x, maxx);
                		maxy=Math.max(y, maxy);
                		               		
                	}
                	m[y][x]=3;
                	}
                	else
                	{
                		minx=miny=0;
                		maxx=Integer.MAX_VALUE-20;
                		maxy=Integer.MAX_VALUE-20;
                	}
                	minmax.add(minx);
                	minmax.add(miny);
                	minmax.add(maxx);
                	minmax.add(maxy);
                	
                	return m;
                }
                
                private Node findGateHorizontal(Node n1,int shift, TileMapData t)
                {
                	int x=n1.x;
                	int y=n1.y;
                	Node n2;
                	for (int k = x; k < TMXLoader.retLayerWidth(t) && k>0; k+=shift){
    					
    					if( map[y][k]<threshold)
    						break;
    					if( map[y][k]>= gate)
    					{
    					if(shift==-1)	
    						 n2= new Node(k-1,y);
    					else
    						n2= new Node(k+1,y);
    					return n2;
    					}
    				}
                	return null;
                }
                private Node findGateVertical(Node n1,int shift, TileMapData t)
                {
                	int x=n1.x;
                	int y=n1.y;
                	Node n2;
                	for (int k = y; k < TMXLoader.retLayerHeight(t) && k>0; k+=shift){
    					
    					if( map[k][x]<threshold)
    						break;
    					if( map[k][x]>= gate)
    					{
    						if(shift==-1)	
       						 n2= new Node(x,k-1);
       					else
       						n2= new Node(x,k+1);
    					
    					return n2;
    					}
    				}
                	return null;
                }
//
//                public static void main(String [] args){
//                                int [][] map = new int[][]{
//                                                {1, 1, 1, 1, 1, 1, 1, 1, 1},
//                                                {0, 0, 1, 0, 0, 0, 0, 0, 1},
//                                                {1, 1, 1, 1, 0, 1, 1, 0, 1},
//                                                {1, 1, 1, 1, 1, 1, 1, 0, 0},
//                                                {1, 0, 0, 0, 0, 1, 0, 0, 0},
//                                                {1, 1, 1, 1, 0, 1, 1, 1, 1},
//                                                {1, 1, 1, 1, 0, 1, 0, 0, 1},
//                                                {1, 1, 1, 1, 0, 1, 0, 0, 1},
//                                                {1, 1, 1, 1, 0, 1, 0, 0, 1},
//                                                {1, 1, 1, 1, 0, 1, 0, 0, 0},
//                                                {1, 1, 1, 1, 0, 1, 1, 1, 1},
//                                };
//                                PathFinder pf = new PathFinder(map);
//
//                                System.out.println("Find a path from the top left corner to the right bottom one.");
//
//                                for(int i = 0; i < map.length; i++){
//                                                for(int j = 0; j < map[0].length; j++)
//                                                                System.out.print(map[i][j] + " ");
//                                                System.out.println();
//                                }
//
//                                long begin = System.currentTimeMillis();
//
//                                List<Node> nodes = pf.compute(new PathFinder.Node(8, 5),new PathFinder.Node(5, 5));
//                                
//                                long end = System.currentTimeMillis();
//
//
//                                System.out.println("Time = " + (end - begin) + " ms" );
//                                System.out.println("Expanded = " + pf.getExpandedCounter());
//                                System.out.println("Cost = " + pf.getCost());
//                                
//                                if(nodes == null)
//                                                System.out.println("No path");
//                                else{
//                                                System.out.print("Path = ");
//                                                for(Node n : nodes)
//                                                                System.out.print(n);
//                                                System.out.println();
//                                }
//                }
//
                
               /* public long[][] pfcompute(int x1,int y1,int x2,int y2,TileMapData t){
                	int minx=Integer.MAX_VALUE;
                	int miny=Integer.MAX_VALUE;
                	int maxx=0;
                	int maxy=0;
                	//route tile value 
                	routeTileValue=(int) map[y1][x1];
                	long [][]m= new long[map.length][map[0].length];
                	ArrayList<Node> aln,als;
                	Node nodeStart=new Node(x1,y1);
                	Node nodeEnd=new Node(x2,y2);
                	aln=new ArrayList<AStar.Node>();
                	aln.add(nodeEnd);
                	aln.add(new Node(37,23));
                	als=new ArrayList<AStar.Node>();
                	als.add(nodeStart);
                	als.add(new Node(0,7));
                	Log.i("POINTS","Start points "+als+"\n Ends points "+aln);
                	
                	List<Node> nodes = compute(als,aln);
                	int count=0;
                	int x = 0,y = 0;
                	if(nodes!=null){
                	for(Node n: nodes)
                	{
                		System.out.println("Path: "+ n);
                		if(count==0)
                		{
                			m[(int) n.y][(int) n.x]=2;
                			count++;
                		}
                		else
                			m[(int) n.y][(int) n.x]=1;
                		
                		x=n.x;
                		y=n.y;
                		
                		               		
                	}
                	m[y][x]=3;
                	}else
                	{
                		minx=miny=0;
                		maxx=Integer.MAX_VALUE-20;
                		maxy=Integer.MAX_VALUE-20;
                	}
                	minmax.add(minx);
                	minmax.add(miny);
                	minmax.add(maxx);
                	minmax.add(maxy);
                	
                	return m;
                }
                */
                public long[][] pfcompute(ArrayList<FacilityInformation> start,ArrayList<FacilityInformation> to,VenueMapWithRoute fragment){
                	
                	
                	ArrayList<Node> aendpoint=new ArrayList<AStar.Node>();
                	ArrayList<Node> astartPoint=new ArrayList<AStar.Node>();
                	Log.i("EFAIR","Start and end point size : "+start.size()+","+to.size());
                	for(int i=0;i<start.size();i++){
                		astartPoint.add(new Node(Integer.parseInt(start.get(i).getxLoc()), Integer.parseInt(start.get(i).getyLoc())));
                		Log.i("EFAIR","Start  positions "+start.get(i).getxLoc()+","+start.get(i).getyLoc());
                	}
                	for(int i=0;i<to.size();i++){
                		aendpoint.add(new Node(Integer.parseInt(to.get(i).getxLoc()), Integer.parseInt(to.get(i).getyLoc())));
                		Log.i("EFAIR","Elevator positions "+to.get(i).getxLoc()+","+to.get(i).getyLoc());
                	}
                	//route tile value 
                	routeTileValue=(int) map[Integer.parseInt(start.get(0).getyLoc())][Integer.parseInt(start.get(0).getxLoc())];
                	long [][]m= new long[map.length][map[0].length];
                	
                	
                	List<Node> nodes = compute(astartPoint,aendpoint);
                	int count=0;
                	int x = 0,y = 0;
                	if(nodes!=null){
                	for(Node n: nodes)
                	{
                		System.out.println("Path: "+ n);
                		if(count==0)
                		{
                			m[(int) n.y][(int) n.x]=2;
                			count++;
                		}
                		else
                			m[(int) n.y][(int) n.x]=1;
                		
                		x=n.x;
                		y=n.y;
                		
                		               		
                	}
                	m[y][x]=3;
                	Log.i("EFAIR","destination elevator find path1 : "+x+","+y);
                	//search destination point 
                	try{
                	for(FacilityInformation facility: to){
                		if(Integer.parseInt(facility.getxLoc())==x && Integer.parseInt(facility.getyLoc())==y){
                			fragment.setFacility(facility);
                			Log.i("EFAIR","destination elevator find path if : "+facility.getxLoc()+","+facility.getyLoc());
                			break;
                		}
                	 }
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                }
                	
                	return m;
             }
                
      public long[][] pfcompute(ArrayList<FacilityInformation> start,ArrayList<FacilityInformation> to,VenueMapRouteTwo fragment){
                	
                	ArrayList<Node> aendpoint=new ArrayList<AStar.Node>();
                	ArrayList<Node> astartPoint=new ArrayList<AStar.Node>();
                	Log.i("EFAIR","Start and end point size : "+start.size()+","+to.size());
                	for(int i=0;i<start.size();i++){
                		astartPoint.add(new Node(Integer.parseInt(start.get(i).getxLoc()), Integer.parseInt(start.get(i).getyLoc())));
                		Log.i("EFAIR","Start  positions "+start.get(i).getxLoc()+","+start.get(i).getyLoc());
                	}
                	for(int i=0;i<to.size();i++){
                		aendpoint.add(new Node(Integer.parseInt(to.get(i).getxLoc()), Integer.parseInt(to.get(i).getyLoc())));
                		Log.i("EFAIR","Elevator positions "+to.get(i).getxLoc()+","+to.get(i).getyLoc());
                	}
                	//route tile value 
                	routeTileValue=(int) map[Integer.parseInt(start.get(0).getyLoc())][Integer.parseInt(start.get(0).getxLoc())];
                	long [][]m= new long[map.length][map[0].length];
                	
                	
                	List<Node> nodes = compute(astartPoint,aendpoint);
                	int count=0;
                	int x = 0,y = 0;
                	if(nodes!=null){
                	for(Node n: nodes)
                	{
                		System.out.println("Path: "+ n);
                		if(count==0)
                		{
                			m[(int) n.y][(int) n.x]=2;
                			count++;
                		}
                		else
                			m[(int) n.y][(int) n.x]=1;
                		
                		x=n.x;
                		y=n.y;
                		
                		               		
                	}
                	m[y][x]=3;
                	Log.i("EFAIR","destination elevator find path1 : "+x+","+y);
                	//search destination point 
                	try{
                	for(FacilityInformation facility: to){
                		if(Integer.parseInt(facility.getxLoc())==x && Integer.parseInt(facility.getyLoc())==y){
                			fragment.setFacility(facility);
                			Log.i("EFAIR","destination elevator find path if : "+facility.getxLoc()+","+facility.getyLoc());
                			break;
                		}
                	 }
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                }
                	
                	return m;
             }   
                
                
                public long[][] pfcompute(int x1,int y1,int x2,int y2,TileMapData t){
                	//threshold=TMXLoader.retTilesetFirstGID(t,1);
                	Log.i("POINTS","threshold : "+threshold);
                	//route tile value 
                	routeTileValue=(int) map[y1][x1];
                	Log.i("EXPO","Route tile no "+routeTileValue);
                	long [][]m= new long[map.length][map[0].length];
                	ArrayList<Node> aln,als;
                	Node nodeStart=new Node(x1,y1);
                	Node nodeEnd=new Node(x2,y2);
                	aln=new ArrayList<AStar.Node>();
                	aln.add(nodeEnd);
                	//aln.add(new Node(37,23));
                	als=new ArrayList<AStar.Node>();
                	als.add(nodeStart);
                	//als.add(new Node(0,7));
                	Log.i("POINTS","Start points "+als+"\n Ends points "+aln);
                	
                	List<Node> nodes = compute(als,aln);
                	Log.i("POINTS","Start points "+nodes);
                	int count=0;
                	int x = 0,y = 0;
                	if(nodes!=null){
                	for(Node n: nodes)
                	{
                		System.out.println("Path: "+ n);
                		if(count==0)
                		{
                			m[(int) n.y][(int) n.x]=2;
                			count++;
                		}
                		else
                			m[(int) n.y][(int) n.x]=1;
                		
                		x=n.x;
                		y=n.y;
                		
                		               		
                	}
                	m[y][x]=3;
                	}
                	
                	return m;
                }
                
                public List<Node> findPath(int x1, int y1,List<FacilityInformation> dest,TileMapData t){
                	
                	Log.i("EXPO","Route tile no "+routeTileValue);
                	routeTileValue=(int) map[y1][x1];
                	ArrayList<Node> aln=new ArrayList<AStar.Node>();
                	Node nodeStart=new Node(x1,y1);
                	ArrayList<Node> als=new ArrayList<AStar.Node>();
                	als.add(nodeStart);
                	for(FacilityInformation d:dest){
                		Node nodeEnd=new Node(Integer.valueOf(d.getxLoc()), Integer.valueOf(d.getyLoc()));
                		aln.add(nodeEnd);
                	}
                	
                	Log.i("POINTS","Start points "+als+"\n Ends points "+aln);
                	
                	List<Node> nodes = compute(als,aln);
                	Log.i("POINTS","Points "+nodes);
                	
                	return nodes;
                }
}

