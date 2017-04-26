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

import java.util.*;


/**
 * A* algorithm implementation using the method design pattern.
 * 
 * @author Giuseppe Scrivano
 */
public abstract class AStar
{
				public static class Node{
					public int x;
					public int y;
//					public int id;
//					private static int gid=1;
					Node(int x, int y){
                        this.x = x; 
                        this.y = y;
//                        this.id=gid;
//                        gid++;
					}
					public String toString(){
                        return "(" + x + ", " + y + ") ";
					} 
					// Use @Override to avoid accidental overloading.
					   @Override 
					   public boolean equals(Object o) {
					     // Return true if the objects are identical.
					     // (This is just an optimization, not required for correctness.)
					     if (this == o) {
					       return true;
					     }

					     // Return false if the other object has the wrong type.
					     // This type may be an interface depending on the interface's specification.
					     if (!(o instanceof Node)) {
					       return false;
					     }

					     // Cast to the appropriate type.
					     // This will succeed because of the instanceof, and lets us access private fields.
					     Node lhs = (Node) o;

					     // Check each field. Primitive fields, reference fields, and nullable reference
					     // fields are all treated differently.
					     return (x == lhs.x && y == lhs.y);
					                
					   }
					   @Override 
					   public int hashCode() {
				           int result = 17;
				           result = 31 * result + x;
				           result = 31 * result + y;
				           
				           return result;
				}
				}
                private class Path implements Comparable{
                                public Node point;
                                public Double f;
                                public Double g;
                                public Path parent;
                                
                                /**
                                 * Default c'tor.
                                 */
                                public Path(){
                                                parent = null;
                                                point = null;
                                                g = f = 0.0;
                                }

                                /**
                                 * C'tor by copy another object.
                                 * 
                                 * @param p The path object to clone.
                                 */
                                public Path(Path p){
                                                this();
                                                parent = p;
                                                g = p.g;
                                                f = p.f;
                                               
                                }

                                /**
                                 * Compare to another object using the total cost f.
                                 *
                                 * @param o The object to compare to.
                                 * @see       Comparable#compareTo()
                                 * @return <code>less than 0</code> This object is smaller
                                 * than <code>0</code>;
                                 *        <code>0</code> Object are the same.
                                 *        <code>bigger than 0</code> This object is bigger
                                 * than o.
                                 */
                                public int compareTo(Object o){
                                               
												Path p = (Path)o;
                                                return (int)(f - p.f);
                                }

                                /**
                                 * Get the last point on the path.
                                 *
                                 * @return The last point visited by the path.
                                 */
                                public Node getPoint(){
                                                return point;
                                }

                                /**
                                 * Set the 
                                 */
                                public void setPoint(Node p){
                                                point = p;
                                               
                                }
                }

                /**
                 * Check if the current node is a goal for the problem.
                 *
                 * @param node The node to check.
                 * @return <code>true</code> if it is a goal, <code>false</else> otherwise.
                 */
                protected abstract boolean isGoal(Node node, ArrayList<Node> end);

                /**
                 * Cost for the operation to go to <code>to</code> from
                 * <code>from</from>.
                 *
                 * @param from The node we are leaving.
                 * @param to The node we are reaching.
                 * @return The cost of the operation.
                 */
                protected abstract Double g(Node from, Node to);

                /**
                 * Estimated cost to reach a goal node.
                 * An admissible heuristic never gives a cost bigger than the real
                 * one.
                 * <code>from</from>.
                 *
                 * @param from The node we are leaving.
                 * @param to The node we are reaching.
                 * @return The estimated cost to reach an object.
                 */
                protected abstract Double h(ArrayList<Node> from, Node to);


                /**
                 * Generate the successors for a given node.
                 *
                 * @param node The node we want to expand.
                 * @return A list of possible next steps.
                 */
                protected abstract List<Node> generateSuccessors(Node node);

                public long[][] map;
                private PriorityQueue<Path> paths;
                private Double mindists[][];
                private Double lastCost;
                private int expandedCounter;

                /**
                 * Check how many times a node was expanded.
                 *
                 * @return A counter of how many times a node was expanded.
                 */
                public int getExpandedCounter(){
                                return expandedCounter;
                }

                /**
                 * Default c'tor.
                 */
                public AStar(long[][] map){
                				this.map=map;
                                paths = new PriorityQueue<Path>();
                                mindists = new Double[map.length][map[0].length];
                                for(int i=0;i<map.length;i++)
                                Arrays.fill(mindists[i], null);
                                expandedCounter = 0;
                                lastCost = 0.0;
                }


                /**
                 * Total cost function to reach the node <code>to</code> from
                 * <code>from</code>.
                 *  
                 * The total cost is defined as: f(x) = g(x) + h(x).
                 * @param from The node we are leaving.
                 * @param to The node we are reaching.
                 * @return The total cost.
                 */
                protected Double f(Path p, Node from, Node to, ArrayList<Node> end){
                                Double g =  g(from, to) + ((p.parent != null) ? p.parent.g : 0.0);
                                Double h = h(end, to);

                                p.g = g;
                                p.f = g + h;

                                return p.f;
                }

                /**
                 * Expand a path.
                 *
                 * @param path The path to expand.
                 */
                private void expand(Path path, ArrayList<Node> end){
//                                Node p = path.getPoint();
//                                Double min = mindists.get(p);
//                                System.out.print(path.getPoint()+""+mindists.get(p));
//                                /*
//                                 * If a better path passing for this point already exists then
//                                 * don't expand it.
//                                 */
//                                if(min == null || min.doubleValue() > path.f.doubleValue())
//                                {               mindists.put(p, path.f);
//                              //  System.out.print(path.getPoint()+""+mindists.get(p));
//                                }
//                                else
//                                                return;

                               // System.out.print(path.getPoint()+""+mindists.get(p));
                                List<Node> successors = generateSuccessors(path.getPoint());

                                for(Node t : successors){
                                                Path newPath = new Path(path);
                                                newPath.setPoint(t);
                                                f(newPath, path.getPoint(), t, end);
                                                paths.add(newPath);
                                                System.out.println("Successors: "+newPath.getPoint()+" "+newPath.f);
                                }

                                expandedCounter++;
                }

                /**
                 * Get the cost to reach the last node in the path.
                 *
                 * @return The cost for the found path.
                 */
                public Double getCost(){
                                return lastCost;
                }


                /**
                 * Find the shortest path to a goal starting from
                 * <code>start</code>.
                 *
                 * @param start The initial node.
                 * @return A list of nodes from the initial point to a goal,
                 * <code>null</code> if a path doesn't exist.
                 */
                public List<Node> compute(ArrayList<Node> start, ArrayList<Node> end){
                                try{
                                               // Path root = new Path();
                                                for(Node tn: start)
                                                {
                                                	Path root = new Path();
                                                	root.setPoint(tn);
                                                
                                                /* Needed if the initial point has a cost.  */
                                                //f(root, start, start);
                                                 mindists[tn.y][tn.x]=root.f;
                                              //  System.out.print(path.getPoint()+""+mindists.get(p));
                                                 expand(root,end);
                                                }
                                                 Path path = new Path();

                                                for(;;){
                                                                path = paths.remove();
                                                                
//                                                                for(Path p : paths)
//                                                                {
//                                                                	System.out.print(paths.peek());
//                                                                }
                                                                
                                                                if(path == null){
                                                                                lastCost = Double.MAX_VALUE;
                                                                                return null;
                                                                }

                                                               // System.out.println(p.getPoint() + "" +p.f);
                                                                Node last = path.getPoint();
                                                                //System.out.print(last+""+mindists.get(last));
                                                                lastCost = path.g;

                                                                if(isGoal(last,end)){
                                                                                LinkedList<Node> retPath = new LinkedList<Node>();

                                                                                for(Path i = path; i != null; i = i.parent){
                                                                                                retPath.addFirst(i.getPoint());
                                                                                }

                                                                                return retPath;
                                                                }
                                                                 
                                                                Double min = mindists[last.y][last.x];
                                                                System.out.println(path.getPoint()+""+mindists[last.y][last.x]);
                                                                /*
                                                                 * If a better path passing for this point already exists then
                                                                 * don't expand it.
                                                                 */
                                                                if(min == null || min.doubleValue() > path.f.doubleValue())
                                                                {               mindists[last.y][last.x]=path.f;
                                                               
                                                              //  System.out.print(path.getPoint()+""+mindists.get(p));
                                                                
                                                                expand(path,end);
                                                                }
                                                }
                                }
                                catch(Exception e){
                                                e.printStackTrace();
                                }
                                return null;
                                                
                }
}