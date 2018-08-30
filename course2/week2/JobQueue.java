/*
Task. You have a program which is parallelized and uses n independent threads to process the given list
of m jobs. Threads take jobs in the order they are given in the input. If there is a free thread,
it immediately takes the next job from the list. If a thread has started processing a job, it doesn't
interrupt or stop until it fnishes processing the job. If several threads try to take jobs from the list
simultaneously, the thread with smaller index takes the job. For each job you know exactly how long
will it take any thread to process this job, and this time is the same for all the threads. You need to
determine for each job which thread will process it and when will it start processing.

Input Format. The first line of the input contains integers n and m.
The second line contains m integers ti | the times in seconds it takes any thread to process i-th job.
The times are given in the same order as they are in the list from which threads take jobs.
Threads are indexed starting from 0.

Constraints. 1 <= n <= 1e5; 1 <= m <= 1e5; 0  ti  109.

Output Format. Output exactly m lines. i-th line (0-based index is used) should contain two space-
separated integers | the 0-based index of the thread which will process the i-th job and the time in
seconds when it will start processing that job.
*/

import java.io.*;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers; //number of threads
    private int[] jobs; //stores job running times

    private int[] assignedWorker; //the corresponding thread of each job
    private long[] startTime; //starting times of the jobs
	
	//next free time points of the threads			
	//initial arrary forms a min-queue based on thread numbering... 
	//...with the same element value	
    Thread[] threads;		
	

    private FastScanner in;
    private PrintWriter out;
	
	public class Thread{
		private long nextFreeTime;
		private int threadNum;
		
		public void Thread(int number){
			this.threadNum = number;
			this.nextFreeTime = 0;
		}
		
		public void updateTime(long time){
			this.nextFreeTime = time;
		}
		
		public long getTime(){
			return this.nextFreeTime;
		}
		
		public int getTN(){
			return this.threadNum;
		}
		
		public void printTime(){
		//	System.out.println("thread #" + this.threadNum + ": " + nextFreeTime);
		}
	}

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt(); //number of threads
        int m = in.nextInt(); //number of jobs
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt(); //job running times
        }
		
		threads = new Thread[numWorkers];
		for (int i = 0; i < numWorkers; ++i){
			threads[i] = new Thread();
			//why the original constructor cannot be rewritten?
			threads[i].Thread(i);
		}
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];		
		Thread bestWorker;		
		int duration;
			
		for (int i = 0; i < jobs.length; i++) {
            duration = jobs[i];			
			bestWorker = extractMax();
            assignedWorker[i] = bestWorker.getTN();
            startTime[i] = bestWorker.getTime();
            bestWorker.updateTime(startTime[i] + (long)duration);
			//System.out.println();
			//System.out.println("job " + i + " assigned, start sifting down...");
			siftDown(0);		
			
			//System.out.println("siftDown finished");
			//for (int j = 0; j < threads.length; ++j)
			//	threads[j].printTime();

				
        }
    }

	private void siftUp(int i){		
		while (i > 0){
			int parent = i / 2;
			
			if (threads[i].getTime() < threads[parent].getTime()){
				swap(i, parent);				
				i = parent;
				parent = i / 2;			
			}
			else if(threads[i].getTime() == threads[parent].getTime()){
				if (threads[i].getTN() < threads[parent].getTN()){
					swap(i, parent);
					i = parent;
					parent = i / 2;
				}
					
			}			
		}
	}
	
	private void siftDown(int i){
		int left  = 2*i + 1;
		//System.out.println("left of " + i + ": " + left);
		int right = 2*i + 2;
		//System.out.println("right of " + i + ": " + right);
		//System.out.println("numWorkers: " + numWorkers);
		int min = i;
		if ((left >= numWorkers) && (right >= numWorkers))
			return;
		//consider both thread number and next free time in moving
		if (left < numWorkers) {			
			if ((threads[min].getTime() == threads[left].getTime()) && 
				(threads[min].getTN() > threads[left].getTN())){
				min = left;
				//System.out.println("left < numWorkers, == left, min= " + min);
			}
			else if (threads[min].getTime() > threads[left].getTime()){
				min = left;
				//System.out.println("left < numWorkers, > left, min= " + min);
			}
			
		}		
		
		if (right < numWorkers) {
			if ((threads[min].getTime() == threads[right].getTime()) && 
				(threads[min].getTN() > threads[right].getTN())){
				min = right;
				//System.out.println("right < numWorkers, == right, min= " + min);
			}
			else if (threads[min].getTime() > threads[right].getTime()){
				min = right;
				//System.out.println("right < numWorkers, > right, min= " + min);
			}
			
		}
						
		if (min != i){
			//System.out.println("min(" + min + ") !" + "= i(" + i + ")");
			swap(i, min);
			siftDown(min);
			}
			//System.out.println("min: " + min);	
	}
	
	private Thread extractMax(){
		return threads[0];
	}

	private void swap(int i, int min){
		Thread temp = threads[i];
		threads[i]     = threads[min];
		threads[min]     = temp;
	}
	
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
