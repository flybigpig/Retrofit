package com.example.baidu.retrofit.net;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author erdigurbuz
 */
public class PingTest extends Thread {

    HashMap<String, Object> result = new HashMap<>();
    String server = "";
    int count;
    double instantRtt = 0;
    double avgRtt = 0.0;
    boolean finished = false;
    boolean started = false;

    public PingTest(String serverIpAddress, int pingTryCount) {
        this.server = serverIpAddress;
        this.count = pingTryCount;
    }

    public double getAvgRtt() {
        return avgRtt;
    }

    public double getInstantRtt() {
        return instantRtt;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder ps = new ProcessBuilder("ping", "-c " + count, this.server);
            Log.d("pings", this.server);
            ps.redirectErrorStream(true);
            Process pr = ps.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                Log.d("pings", line.toString());
                if (line.contains("icmp_seq")) {
                    instantRtt = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                }
                if (line.startsWith("rtt ")) {
                    avgRtt = Double.parseDouble(line.split("/")[4]);
                    break;
                }
                if (line.contains("Unreachable") || line.contains("Unknown") || line.contains("%100 packet loss")) {
                    return;
                }
            }
            pr.waitFor();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        finished = true;
    }

}
