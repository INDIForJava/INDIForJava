/*
 *  This file is part of INDI for Java Android UI.
 * 
 *  INDI for Java Android UI is free software: you can redistribute it
 *  and/or modify it under the terms of the GNU General Public License 
 *  as published by the Free Software Foundation, either version 3 of 
 *  the License, or (at your option) any later version.
 * 
 *  INDI for Java Android UI is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with INDI for Java Android UI.  If not, see 
 *  <http://www.gnu.org/licenses/>.
 */
package laazotea.indi.androidui;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TabHost.TabSpec;
import android.widget.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import laazotea.indi.INDIException;
import laazotea.indi.client.INDIDevice;
import laazotea.indi.client.INDIServerConnection;
import laazotea.indi.client.INDIServerConnectionListener;

/**
 * An Android Activity that implements a INDI Basic Client.
 *
 * @version 1.32, April 19, 2012
 * @author S. Alonso (Zerjillo) [zerjio at zerjio.com]
 */
public class INDIAndroidBasicActivity extends TabActivity implements INDIServerConnectionListener {

  private Handler handler;
  private TabHost tabs;
  private LinearLayout connectionTab;
  private TextView hostText;
  private EditText host;
  private TextView portText;
  private EditText port;
  private Button connectionButton;
  private Button disconnectionButton;
  private ArrayList<INDIDevice> devices;
  private INDIAndroidApplication app;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    app = (INDIAndroidApplication) this.getApplication();

    final Context context = this.getApplicationContext();
    handler = new Handler();

    I4JAndroidConfig.setContext(context);
    I4JAndroidConfig.setHandler(handler);

    tabs = getTabHost();

    devices = new ArrayList<INDIDevice>();
             
    createConnectionView();

    // We need to check if the connection was already created (maybe the activity has reestarded due to some configuration change)

    INDIServerConnection conn = app.getConnection();

    if (conn != null) {  // Update the interface
      String h = conn.getHost();
      int p = conn.getPort();

      host.setText(h);   // We update the connection tab interface
      port.setText("" + p);
      connectionButton.setEnabled(false);

      List<INDIDevice> dds = conn.getDevicesAsList();

      for (int i = 0; i < dds.size(); i++) {
        INDIDevice d = dds.get(i);

        addD(d);  // We add the interface for this device
      }
    }
  }

  private void createConnectionView() {
    connectionTab = new LinearLayout(this);
    connectionTab.setOrientation(LinearLayout.VERTICAL);

    hostText = new TextView(this);
    hostText.setText("Host:");
    host = new EditText(this);
    host.setSingleLine();
    host.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    LinearLayout l = new LinearLayout(this);
    l.addView(hostText);
    l.addView(host);
    connectionTab.addView(l);

    portText = new TextView(this);
    portText.setText("Port:");
    port = new EditText(this);
    port.setSingleLine();
    port.setText("7624");
    port.setLayoutParams(new android.widget.LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    l = new LinearLayout(this);
    l.addView(portText);
    l.addView(port);
    connectionTab.addView(l);

    connectionButton = new Button(this);
    connectionButton.setText("Connect");
    connectionButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        connect();
      }
    });
    connectionTab.addView(connectionButton);

    disconnectionButton = new Button(this);
    disconnectionButton.setText("Disconnect");
    disconnectionButton.setEnabled(false);
    disconnectionButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        disconnect();
      }
    });
    connectionTab.addView(disconnectionButton);


    TabSpec tspec = tabs.newTabSpec("CONNECTION").setIndicator("Connection", getResources().getDrawable(R.drawable.ic_network));
    tspec.setContent(new TabHost.TabContentFactory() {

      public View createTabContent(String tag) {
        return connectionTab;
      }
    });

    tabs.addTab(tspec);
  }

  private void connect() {
    String hostName = host.getText().toString();
    String portString = port.getText().toString();

    int portNumber = 7624;

    try {
      portNumber = Integer.parseInt(portString);
    } catch (NumberFormatException e) {
      return;
    }

    INDIServerConnection sc = new INDIServerConnection("xXx", hostName, portNumber);
    try {
      sc.connect();

      sc.addINDIServerConnectionListener(this);

      sc.askForDevices(); // Ask for all the devices.

      app.setConnection(sc);  // We save the connection into the application class
    } catch (IOException e) {
      return;
    }

    connectionButton.setEnabled(false);
    disconnectionButton.setEnabled(true);
  }

  public void disconnect() {
    if (app.getConnection() != null) {
      app.getConnection().disconnect();

      host.setText("");
      port.setText("7624");

      connectionButton.setEnabled(true);
      disconnectionButton.setEnabled(false);
    }
  }

  public void newDevice(INDIServerConnection connection, final INDIDevice device) {
    try {
      I4JAndroidConfig.postHandler(new Runnable() {

        @Override
        public void run() {
          addD(device);
        }
      });
    } catch (INDIException e) {
      e.printStackTrace();
    }
  }

  private void addD(INDIDevice device) {
    try {
      final INDIDeviceView view = (INDIDeviceView) device.getDefaultUIComponent();

      TabSpec spec = tabs.newTabSpec(device.getName()).setIndicator(device.getName(), getResources().getDrawable(R.drawable.ic_gear));
      spec.setContent(new TabHost.TabContentFactory() {

        public View createTabContent(String tag) {
          return view;
        }
      });

      tabs.addTab(spec);
    } catch (INDIException ee) {
      ee.printStackTrace();
    }

    devices.add(device);
  }

  public void removeDevice(INDIServerConnection connection, final INDIDevice device) {
    try {
      I4JAndroidConfig.postHandler(new Runnable() {

        @Override
        public void run() {
          removeD(device);
        }
      });
    } catch (INDIException e) {
      e.printStackTrace();
    }
  }

  private void removeD(INDIDevice device) {
    // Here we should include the code to remove the tabs.
  }

  public void connectionLost(INDIServerConnection connection) {
    // Here we should include the code to handle the disconnection
  }

  public void newMessage(INDIServerConnection connection, Date date, String message) {
    // Here we should include the code to handle messages
  }
}
