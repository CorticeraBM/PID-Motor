package com.example.pid_motor_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        tb = (ToggleButton)findViewById(R.id.btn_toggle);
        tb.setTextOff("Start");
        tb.setTextOn("Stop");

        tb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Change", Toast.LENGTH_SHORT).show();

                if (tb.isChecked()) {

                    ArduHelper.Send( 0);

                } else {

                    ArduHelper.Send( 1);

                }//if-else block


            }//onClick func
        });

        setContentView(tb);

    }//onCreate func

}//MainActivity class

class ArduHelper {

    public static void Send(int character){

        try {

            BluetoothAdapter adp = BluetoothAdapter.getDefaultAdapter();

            Set<BluetoothDevice> pairedDevices = adp.getBondedDevices();

            BluetoothDevice ardu = null;

            for (BluetoothDevice cihaz : pairedDevices)
            {
                if (cihaz.getName() == "HC-06")
                {
                    ardu = cihaz;
                    break;
                }
            }

            if (ardu != null)
            {
                UUID uuid = UUID.fromString("20:13:04:24:20:51");
                BluetoothSocket socket = ardu.createRfcommSocketToServiceRecord(uuid);
                socket.connect();
                OutputStream stream = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(stream);
                osw.write(65);
                osw.close();
                stream.close();
                socket.close();
            }
        }
        catch (Exception e)
        {

        }
    }
}
