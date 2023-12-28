package com.example.nexashare.FCM;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;



import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class FCMSend {
    private static String BASE_URL = "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY = "key=AAAAJglKiWM:APA91bHrLxBAvmeyS2xnQCdgxLctT3HI9B9Bc7URlDyk4WOJL7kkwcHmxyZjTO0YizVlXy_DUNZcIC4skvfkWmeTZuAspjMGIvlYIJZR9XYWS-t4dM8WAO4GkXDYSMPVKlZtbt_isKmH";

    public static void pushNotification(Context context,String userId, String token, String title, String message) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject json = new JSONObject();
            json.put("to", token);
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);
            notification.put("body", message);
            json.put("notification", notification);

//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL,json,  new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.e(TAG, String.valueOf(response));
//                    System.out.println("FCM " + response);
//                }
//            },new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("Content-Type","application/json");
//                    params.put("Authorization",SERVER_KEY);
//
//                    return params;
//                }
//            };
//            queue.add(jsonObjectRequest);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // If the notification was sent successfully, store the notification details in Firestore
                    storeNotificationInFirestore(userId, token, title, message);

                    // Log or handle the response
                    Log.e(TAG, String.valueOf(response));
                    System.out.println("FCM " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("Authorization",SERVER_KEY);

                    return params;
                }
            };

            queue.add(jsonObjectRequest);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    private static void storeNotificationInFirestore(String userId, String token, String title, String message) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a map with notification details
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("userId", userId);
        notificationData.put("token", token);
        notificationData.put("title", title);
        notificationData.put("message", message);
        notificationData.put("timestamp", FieldValue.serverTimestamp());

        // Add the notification under a "notifications" collection and "sentNotifications" subcollection
        // Each notification will have its own document ID (auto-generated by Firestore)
        db.collection("notifications")
                .document(userId)
                .collection("sentNotifications")
                .add(notificationData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Notification data added to Firestore successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure to add notification data
                        Log.d(TAG, "Failed to store notification " + e);
                    }
                });
    }

}