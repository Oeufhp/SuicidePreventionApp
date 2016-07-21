package com.latte.oeuff.suicidepreventionapp;

//https://github.com/johnjohndoe/AndroidPlot

import android.content.Intent;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

// The simplest possible example of using AndroidPlot to plot some data.
public class SurveyVisualization_Cage extends AppCompatActivity {
    //----getIntent--------
    Intent it;
    String username,password, sentdate, str_totalscore;
    int totalscore;

    //---------- pie 1 ------------------
    private PieChart pie; //a pie chart
    //--- piechart secments ---
    private Segment s1;
    private Segment s2;
    private Segment s3;
    private Segment s4;
    //----textView & seek bar -----
    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    //---------- pie 2 ------------------
    private PieChart pie2;
    //--- piechart secments ---
    private Segment s1_2;
    private Segment s2_2;
    private Segment s3_2;
    private Segment s4_2;
    //----textView & seek bar -----
    private TextView donutSizeTextView2;
    private SeekBar donutSizeSeekBar2;

    //---------- pie 3 ------------------
    private PieChart pie3;
    //--- piechart secments ---
    private Segment s1_3;
    private Segment s2_3;
    private Segment s3_3;
    private Segment s4_3;
    //----textView & seek bar -----
    private TextView donutSizeTextView3;
    private SeekBar donutSizeSeekBar3;

    //---------- pie 2 ------------------
    private PieChart pie4;
    //--- piechart secments ---
    private Segment s1_4;
    private Segment s2_4;
    private Segment s3_4;
    private Segment s4_4;
    //----textView & seek bar -----
    private TextView donutSizeTextView4;
    private SeekBar donutSizeSeekBar4;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_visualization_cage);
        //----------getIntent---------
        it =getIntent();
        username = it.getStringExtra("username");
        password = it.getStringExtra("password");
        sentdate = it.getStringExtra("sentdate");
        str_totalscore = it.getStringExtra("str_totalscore");

        totalscore = Integer.parseInt(str_totalscore);

        //-------- Binding ----------------------------------
        //pie1
        pie = (PieChart) findViewById(R.id.mySimplePieChart);
        donutSizeTextView = (TextView) findViewById(R.id.donutSizeTextView);
        donutSizeSeekBar = (SeekBar) findViewById(R.id.donutSizeSeekBar);
        //pie2
        pie2= (PieChart)findViewById(R.id.mySimplePieChart2);
        donutSizeSeekBar2 = (SeekBar) findViewById(R.id.donutSizeSeekBar2);
        donutSizeTextView2 = (TextView) findViewById(R.id.donutSizeTextView2);
        //pie3
        pie3= (PieChart)findViewById(R.id.mySimplePieChart3);
        donutSizeSeekBar3 = (SeekBar) findViewById(R.id.donutSizeSeekBar3);
        donutSizeTextView3 = (TextView) findViewById(R.id.donutSizeTextView3);
        //pie4
        pie4 = (PieChart)findViewById(R.id.mySimplePieChart4);
        donutSizeSeekBar4 = (SeekBar) findViewById(R.id.donutSizeSeekBar4);
        donutSizeTextView4 = (TextView) findViewById(R.id.donutSizeTextView4);

        //------------------------ My Logics -------------------------------

        //====================== 1. a seek bar ===========================
        donutSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int i, boolean b
            ) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                        PieRenderer.DonutMode.PERCENT);
                pie.redraw();
                donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
            }
        });
        donutSizeSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int i, boolean b
            ) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie2.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                        PieRenderer.DonutMode.PERCENT);
                pie2.redraw();
                donutSizeTextView2.setText(donutSizeSeekBar2.getProgress() + "%");
            }
        });
        donutSizeSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int i, boolean b
            ) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie3.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                        PieRenderer.DonutMode.PERCENT);
                pie3.redraw();
                donutSizeTextView3.setText(donutSizeSeekBar3.getProgress() + "%");
            }
        });
        donutSizeSeekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(
                    SeekBar seekBar, int i, boolean b
            ) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pie4.getRenderer(PieRenderer.class).setDonutSize(seekBar.getProgress()/100f,
                        PieRenderer.DonutMode.PERCENT);
                pie4.redraw();
                donutSizeTextView4.setText(donutSizeSeekBar4.getProgress() + "%");
            }
        });

        //==================== 2. a pie chart =====================================

        //---- 2.1 contents in a pie chart ------------------------
        //*** IMPORTANT 2 cases: 1.show (totalscore >= 2) 2. not show (else)
        if (totalscore >= 2) {
            pieChart(76);
            pieChart2(93);
            pieChart3(77);
            pieChart4(91);
        }
        else {
            pieChart(0);
            pieChart2(0);
            pieChart3(0);
            pieChart4(0);
        }

        //------ 2.2 Detect segment clicks---------
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click
                        //System.out.println("Clicked pie Segment: " + segment.getTitle());
                        if(segment.getTitle().equals("76%")){
                            Toast.makeText(getApplicationContext(),"specificity: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        else if(segment.getTitle().equals("24%")){
                            Toast.makeText(getApplicationContext(),"safe: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
        pie2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie2.getPieWidget().containsPoint(click)) {
                    Segment segment = pie2.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click
                        //System.out.println("Clicked pie Segment: " + segment.getTitle());
                        if(segment.getTitle().equals("93%")){
                            Toast.makeText(getApplicationContext(),"sensitivity: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        else if(segment.getTitle().equals("7%")){
                            Toast.makeText(getApplicationContext(),"safe: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
        pie3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie3.getPieWidget().containsPoint(click)) {
                    Segment segment = pie3.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click
                        //System.out.println("Clicked pie Segment: " + segment.getTitle());
                        if(segment.getTitle().equals("77%")){
                            Toast.makeText(getApplicationContext(),"sensitivity: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        else if(segment.getTitle().equals("23%")){
                            Toast.makeText(getApplicationContext(),"safe: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
        pie4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie4.getPieWidget().containsPoint(click)) {
                    Segment segment = pie4.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        // handle the segment click
                        //System.out.println("Clicked pie Segment: " + segment.getTitle());
                        if(segment.getTitle().equals("91%")){
                            Toast.makeText(getApplicationContext(),"sensitivity: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                        else if(segment.getTitle().equals("9%")){
                            Toast.makeText(getApplicationContext(),"safe: " + segment.getTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });

    }

    //----------- pieChart() ----------------------------------
    public void pieChart(int percent) {
        //--- 1. getValues to create a piechart (each segment) ------------
        s1 = new Segment(percent+"%", percent);
        s2 = new Segment((100-percent)+"%", 100-percent);
//        s3 = new Segment("s3", 10);
//        s4 = new Segment("s4", 10);

        //--- 2. create SegmentFormatters ----------------------------------
        EmbossMaskFilter emf = new EmbossMaskFilter( new float[]{1, 1, 1}, 0.4f, 10, 8.2f );

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
        sf2.getFillPaint().setMaskFilter(emf);

//        SegmentFormatter sf3 = new SegmentFormatter();
//        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);
//        sf3.getFillPaint().setMaskFilter(emf);
//
//        SegmentFormatter sf4 = new SegmentFormatter();
//        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);
//        sf4.getFillPaint().setMaskFilter(emf);

        //---3. Create (add a new series' to the xyplot)---
        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);
        //pie.addSeries(s3, sf3);
        //pie.addSeries(s4, sf4);
        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.rgb(169,169,169));
        //pie.setBackgroundResource(R.drawable.appbg);
    }

    //----------- pieChart2() ----------------------------------
    public void pieChart2(int percent) {
        //--- 1. getValues to create a piechart (each segment) ------------
        s1_2 = new Segment(percent+"%", percent);
        s2_2 = new Segment((100-percent)+"%", 100-percent);
//        s3_2 = new Segment("s3_2", 10);
//        s4_2 = new Segment("s4_2", 10);

        //--- 2. create SegmentFormatters ----------------------------------
        EmbossMaskFilter emf = new EmbossMaskFilter( new float[]{1, 1, 1}, 0.4f, 10, 8.2f );

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
        sf2.getFillPaint().setMaskFilter(emf);

//        SegmentFormatter sf3 = new SegmentFormatter();
//        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);
//        sf3.getFillPaint().setMaskFilter(emf);
//
//        SegmentFormatter sf4 = new SegmentFormatter();
//        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);
//        sf4.getFillPaint().setMaskFilter(emf);

        //---3. Create (add a new series' to the xyplot)---
        pie2.addSeries(s1_2, sf1);
        pie2.addSeries(s2_2, sf2);
//        pie2.addSeries(s3_2, sf3);
//        pie2.addSeries(s4_2, sf4);
        pie2.getBorderPaint().setColor(Color.TRANSPARENT);
        pie2.getBackgroundPaint().setColor(Color.rgb(169,169,169));
    }

    //----------- pieChart3() ----------------------------------
    public void pieChart3(int percent) {
        //--- 1. getValues to create a piechart (each segment) ------------
        s1_3 = new Segment(percent+"%", percent);
        s2_3 = new Segment((100-percent)+"%", 100 - percent);
//        s3_3 = new Segment("s3_3", 5);
//        s4_3 = new Segment("s4_3", 6);

        //--- 2. create SegmentFormatters ----------------------------------
        EmbossMaskFilter emf = new EmbossMaskFilter( new float[]{1, 1, 1}, 0.4f, 10, 8.2f );

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);
        sf3.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf4 = new SegmentFormatter();
        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);
        sf4.getFillPaint().setMaskFilter(emf);

        //---3. Create (add a new series' to the xyplot)---
        pie3.addSeries(s1_3, sf1);
        pie3.addSeries(s2_3, sf2);
//        pie3.addSeries(s3_3, sf3);
//        pie3.addSeries(s4_3, sf4);
        pie3.getBorderPaint().setColor(Color.TRANSPARENT);
        pie3.getBackgroundPaint().setColor(Color.rgb(169,169,169));
    }
    //----------- pieChart4() ----------------------------------
    public void pieChart4(int percent) {
        //--- 1. getValues to create a piechart (each segment) ------------
        s1_4 = new Segment(percent+"%", percent);
        s2_4 = new Segment((100-percent)+"%", 100-percent);
//        s3_4 = new Segment("s3", 10);
//        s4_4 = new Segment("s4", 10);

        //--- 2. create SegmentFormatters ----------------------------------
        EmbossMaskFilter emf = new EmbossMaskFilter( new float[]{1, 1, 1}, 0.4f, 10, 8.2f );

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);
        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);
        sf2.getFillPaint().setMaskFilter(emf);

//        SegmentFormatter sf3 = new SegmentFormatter();
//        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);
//        sf3.getFillPaint().setMaskFilter(emf);
//
//        SegmentFormatter sf4 = new SegmentFormatter();
//        sf4.configure(getApplicationContext(), R.xml.pie_segment_formatter4);
//        sf4.getFillPaint().setMaskFilter(emf);

        //---3. Create (add a new series' to the xyplot)---
        pie4.addSeries(s1_4, sf1);
        pie4.addSeries(s2_4, sf2);
//        pie4.addSeries(s3_4, sf3);
//        pie4.addSeries(s4_4, sf4);
        pie4.getBorderPaint().setColor(Color.TRANSPARENT);
        pie4.getBackgroundPaint().setColor(Color.rgb(169,169,169));
    }
}

