package com.chirag.sudoku;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ClientThread extends Thread{

	 private final BluetoothSocket mmSocket;
	 private final BluetoothDevice mmDevice;
	 BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	 final UUID MY_UUID = UUID.fromString("05c0bd24-c242-11e3-b4b6-b6ee55aeb395");
	 Context context;
	 Handler mHandler;
	    public ClientThread(BluetoothDevice device,BluetoothAdapter mBluetoothAdapter,Context context,Handler mHandler) {
	        // Use a temporary object that is later assigned to mmSocket,
	        // because mmSocket is final
	    	this.context = context;
	    	this.mBluetoothAdapter = mBluetoothAdapter;
	    	this.mHandler = mHandler;
	        BluetoothSocket tmp = null;
	        mmDevice = device;
	 
	        // Get a BluetoothSocket to connect with the given BluetoothDevice
	        try {
	            // MY_UUID is the app's UUID string, also used by the server code
	            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
	        } catch (IOException e) { }
	        mmSocket = tmp;
	    }
	 
	    public void run() {
	        // Cancel discovery because it will slow down the connection
	        mBluetoothAdapter.cancelDiscovery();
	 
	        try {
	            // Connect the device through the socket. This will block
	            // until it succeeds or throws an exception
	            mmSocket.connect();
	        } catch (IOException connectException) {
	            // Unable to connect; close the socket and get out
	            try {
	                mmSocket.close();
	            } catch (IOException closeException) { }
	            return;
	        }
	 
	        // Do work to manage the connection (in a separate thread)
//	        manageConnectedSocket(mmSocket);
	       // System.out.println("Connected");
	        //ConnectedSocket con = new ConnectedSocket(context);
//	        BluetoothMode bt = new BluetoothMode();
	        MainActivity.mainSocket = mmSocket;
	        Message msg = Message.obtain();
	        msg.obj =  10;
	        mHandler.sendMessage(msg);
	//        Runnable r = new Runnable(){

				//@Override
//				public void run() {
	//            	Intent i = new Intent(context, SetBoard.class);
	  //          		startActivity(i);
		//			}
	      //  	
	        //};
	    }
	 
	    /** Will cancel an in-progress connection, and close the socket */
	    public void cancel() {
	        try {
	            mmSocket.close();
	        } catch (IOException e) { }
	    }
}
