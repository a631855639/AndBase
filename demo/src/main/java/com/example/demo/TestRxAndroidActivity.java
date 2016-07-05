package com.example.demo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.helen.andbase.activity.HTitleActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class TestRxAndroidActivity extends HTitleActivity {
	public static final String TAG = TestRxAndroidActivity.class.getSimpleName();
	private TextView msg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(android.R.layout.simple_list_item_1);
		setTitle("测试RxAndroid");
		msg=(TextView) findViewById(android.R.id.text1);
		//test1();
		//test3();
		//test4();
//		test5();
//		test6();
		test7();
	}

	/**
	 * Observable.create
	 */
	private void test1() {
		Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				subscriber.onStart();
				subscriber.onNext("1");
				subscriber.onNext("2");
				subscriber.onCompleted();
			}
		});
		observable.subscribeOn(AndroidSchedulers.mainThread()).subscribe();
	}

	/**
	 * Action1
	 */
	private void test2(){
		Observable.just(1,2,3,4)
				.map(new Func1<Integer, String>() {
					@Override
					public String call(Integer integer) {
						return integer.toString();
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<String>() {
					@Override
					public void call(String integer) {
						showResult("number:"+integer);
					}
				});
	}

	/**
	 * Observer
	 */
	private void test3(){
		Observable.create(new Observable.OnSubscribe<Drawable>() {
			@Override
			public void call(Subscriber<? super Drawable> subscriber) {
				Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
				subscriber.onNext(drawable);
				subscriber.onCompleted();
			}
		}).subscribe(new Observer<Drawable>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Drawable drawable) {
				msg.setText("test3");
				msg.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			}
		});
	}

	/**
	 * subscribeOn\observeOn\Schedulers
	 */
	private void test4(){
		/*Observable.just(1,2,3,4)
				.subscribeOn(Schedulers.io())//指定 subscribe() 发生在 IO 线程
				.observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 的回调发生在主线程
				.subscribe();*/
		Observable.create(new Observable.OnSubscribe<Drawable>() {
			@Override
			public void call(Subscriber<? super Drawable> subscriber) {
				Log.d("Thread-Observable","name:"+Thread.currentThread().getName()+" number:"+Thread.currentThread().getId());
				Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
				subscriber.onNext(drawable);
				subscriber.onCompleted();
			}
		})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Drawable>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				Log.d("Observable","onError",e);
			}

			@Override
			public void onNext(Drawable drawable) {
				Log.d("Thread-Observable","name:"+Thread.currentThread().getName()+" number:"+Thread.currentThread().getId());
				msg.setText("test3");
				msg.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
			}
		});
	}

	/**
	 * Observable.map
	 */
	private void test5(){
		Observable.just(R.drawable.loading)
				.map(new Func1<Integer, Drawable>() {
					@Override
					public Drawable call(Integer integer) {
						return getResources().getDrawable(integer);
					}
				})
				.subscribe(new Action1<Drawable>() {
					@Override
					public void call(Drawable drawable) {
						msg.setCompoundDrawablesWithIntrinsicBounds(null, drawable,null,null);
					}
				});
	}

	private void test6(){
		Student[] students = new Student[]{new Student("张三"),new Student("李四"),new Student("王五")};
		Observable.from(students)
				.map(new Func1<Student, String>() {
					@Override
					public String call(Student student) {
						return student.name;
					}
				})
				.subscribe(new Action1<String>() {
					@Override
					public void call(String s) {
						Log.d("Observable",s);
					}
				});
	}

	/**
	 * flatMap
	 */
	Subscriber<Student> subscriber = new Subscriber<Student>() {
		@Override
		public void onCompleted() {

		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(Student student) {
			List<Student.Course> courses = student.courses;
			Log.d("onNext",student.name+"--------");
			for (Student.Course course : courses){
				Log.d("onNext",course.courseName);
			}
		}
	};
	private void test7(){
		Student[] students = new Student[]{new Student("张三"),new Student("李四"),new Student("王五")};
		//第一种实现
		/*Subscriber<Student> subscriber = new Subscriber<Student>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Student student) {
				List<Student.Course> courses = student.courses;
				Log.d("onNext",student.name+"--------");
				for (Student.Course course : courses){
					Log.d("onNext",course.courseName);
				}
			}
		};*/
		Observable.from(students)
				.subscribe(subscriber);
		/*Observable.from(students)
				.flatMap(new Func1<Student, Observable<Student.Course>>() {
					@Override
					public Observable<Student.Course> call(Student student) {
						return Observable.from(student.courses);
					}
				})
				.subscribe(new Action1<Student.Course>() {
					@Override
					public void call(Student.Course course) {
						Log.d(TAG,course.courseName);
					}
				});*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(subscriber!= null && !subscriber.isUnsubscribed()){
			subscriber.unsubscribe();
		}
	}

	private class Student{
		public String name;
		public List<Course> courses = new ArrayList<>();
		public Student(String name){
			this.name = name;
			courses.add(new Course("语文"));
			courses.add(new Course("数学"));
			courses.add(new Course("英语"));
		}

		public class Course{
			public String courseName;
			public Course(String courseName){
				this.courseName = courseName;
			}
		}
	}

	private void showResult(String msgStr){
		msg.setText(msgStr);
	}
}
