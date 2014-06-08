package com.chirag.sudoku;

import java.io.IOException;
import java.util.UUID;

import android.R.string;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ServerThread extends Thread {

	private final BluetoothServerSocket mmServerSocket;
	BluetoothAdapter mBluetoothAdapter;// = BluetoothAdapter.getDefaultAdapter();
	final String NAME = "order and chaos";
	final UUID MY_UUID = UUID.fromString("05c0bd24-c242-11e3-b4b6-b6ee55aeb395");
	Context context;
	Handler mHandler;
    public ServerThread(BluetoothAdapter mBluetoothAdapter,Context context,Handler mHandler) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
    	this.context = context;
    	this.mBluetoothAdapter = mBluetoothAdapter;
    	this.mHandler = mHandler;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }
 
    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } 
            catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
//                manageConnectedSocket(socket);
            	MainActivity.mainSocket = socket;
            	Message msg = Message.obtain();
    	        msg.obj =  20;
    	        mHandler.sendMessage(msg);
            }
            
            if(Thread.interrupted())
            {
            	cancel();
            	return;
            }
        }
        
    }
 
    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}
