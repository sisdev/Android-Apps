package com.assusoft.eFairEmall.util;

public class ProductsService {///extends Service {
	/*
	public static int productNotiId=101;
	ProductsTask productTask;
	@Override
	public void onCreate() {
		super.onCreate();
		productTask=new ProductsTask();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Products product =intent.getParcelableExtra("products");
		productTask.execute(product);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	*/
	/*class ProductsTask extends AsyncTask<Products, Void, String>
	{

		@Override
		protected String doInBackground(Products... params) {
			Products product=params[0];
			String message=null;
			while(message==null)
			{
				message=WebService.submitProducts(product);
				if(message==null)
					try{ Thread.sleep(10000);}catch (Exception e) {}
			}
			return message;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Notification noti=new Notification(R.drawable.sym_def_app_icon, result, System.currentTimeMillis());
			Intent intent=new Intent(EFairEmallApplicationContext.getEFairEmallApplicationContext(),HomeActivity.class);
			PendingIntent pi=PendingIntent.getActivity(EFairEmallApplicationContext.getEFairEmallApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			noti.setLatestEventInfo(EFairEmallApplicationContext.getEFairEmallApplicationContext(), "EFairEmall", result,pi);
			noti.defaults=Notification.DEFAULT_ALL;
			NotificationManager nm=(NotificationManager) EFairEmallApplicationContext.getEFairEmallApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
			nm.notify(productNotiId,noti);
			//stopSelf();for testing............
		}
	}*/
}
