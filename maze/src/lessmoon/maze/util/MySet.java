package lessmoon.maze.util;

import java.util.TreeSet;
import java.util.Vector;


/**
 * 
 * @author lessmoon
 *
 * @param <T> the type of data been contained
 * 
 * A typic Set Collection for some use
 * And it just contained the data's reference,so you should be careful to use FindContainerOf()
 * and Remove(),because if you have changed the data you store in this container,you should know 
 * we thinks you HAVE KNOWN this collection's data is changed also
 */
public class MySet<T>{
	
		public MySet(){
			
		}

		/**
		 * @param Node
		 * Insert a Node to this Set
		 */
		public void Insert(TreeSet<T> Node){
			C.add(Node);
		}

		/**
		 * @param Data
		 * Find the container of Data
		 */
		public TreeSet<T> FindContainerOf(T Data){
			for(TreeSet<T> i : C){
				if(i.contains(Data)){
					return i;
				}
			}
			return null;
		}

		/**
		 * @param Data
		 * @return TreeSet<T> Where this Data is located
		 * Insert a data to a new container in this collection
		 */
		public TreeSet<T> Insert(T Data){
			TreeSet<T> S = new TreeSet<T>();
			S.add(Data);
			C.add(S);
			return S;
		}

		/**
		 * @param Data
		 * @return true if this Data is in this container
		 */
		public boolean Remove(T Data){
			TreeSet<T> Tree = FindContainerOf(Data);
			if(	Tree != null ){
				Tree.remove(Data);
				return true;
			}
			return false;
		}

		/**
		 * @param Node
		 * @return true if Node is in this container
		 */
		public boolean Remove(TreeSet<T> Node){
			return C.remove(Node);
		}

		/**
		 * @param Node1
		 * @param Node2
		 * @return return the set after union
		 * the Node1 and Node2 must be different and in this collection
		 */
		public TreeSet<T> Union(TreeSet<T> Node1,TreeSet<T> Node2){
			if(Node1 == null
				||Node2 == null
				||Node1 == Node2){
				return Node1;
			}

			Node1.addAll(Node2);
			C.remove(Node2);
			return Node1;
		}

		/**
		 * @return the number of sets in this collection
		 */
		public int Size(){
			return C.size();
		}

		/**
		 * Present the collection(for debug)
		 */
		public void Show(){
			int x = 0;
			for(TreeSet<T> i : C){
				System.out.print(x++);
				System.out.print(":");
				for(T item : i){
					System.out.print(item + " ");
				}
				
				System.out.print("\n");
			}
		}

        /**
         * Clear all node
         */
        
        public void Clear(){
            C.clear();
        }
        
		/**
		 * @return Get the main Cache for the collection
		 */
		public Vector<TreeSet<T>> GetCache(){
			return C;
		}

		private Vector<TreeSet<T>> C = new Vector<TreeSet<T>>();//the Cache vector for the collection
}