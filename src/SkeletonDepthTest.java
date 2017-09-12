/**
 * Created by ricar on 21/08/2017.
 */
import java.util.ArrayList;
import java.util.Locale;

import KinectPV2.KJoint;
import KinectPV2.KinectPV2;
import KinectPV2.KSkeleton;
import processing.core.*;
import root.Logica;
import root.Pantalla;


public class SkeletonDepthTest  extends PApplet{

    KinectPV2 kinect;
    boolean estatura=true;
    private int tiempo=0;
    public KJoint[] joints;
    PImage fondo;
    int sestate=0;
    int contSentadilla;
    float piex,piey,cabex,cabey=0;

    private Logica logica;


    public static void main(String[] args) {
        PApplet.main(new String[] { "SkeletonDepthTest"});
    }

    public void settings(){
        fullScreen();
    }
    public void setup() {
        Pantalla.app = this;
        logica=new Logica(this);
        kinect = new KinectPV2(this);
        //Enables depth and Body tracking (mask image)
        kinect.enableBodyTrackImg(true);
        kinect.enableDepthMaskImg(true);
        //kinect.enableDepthImg(true);
        kinect.enableSkeletonDepthMap(true );
        kinect.init();

        // skeleton = new Skeleton[6];
    }

    public void draw() {
        background(255);

        image(kinect.getBodyTrackImage(), 0, 0);
        image(kinect.getDepthMaskImage(), 512, 0);
        // image(kinect.getDepthImage(), 0, 0);
        ArrayList skeletonArray =  kinect.getSkeletonDepthMap();

        //individual JOINTS
        for (int i = 0; i < skeletonArray.size(); i++) {
            KSkeleton skeleton = (KSkeleton) skeletonArray.get(i);
            if (skeleton.isTracked()) {
                KJoint[] joints = skeleton.getJoints();
                joints = skeleton.getJoints();
                //squatAngleSusteain(joints[KinectPV2.JointType_HipRight],joints[KinectPV2.JointType_KneeRight],joints[KinectPV2.JointType_FootRight],130,5);
                //unApoyo(joints[KinectPV2.JointType_FootLeft],joints[KinectPV2.JointType_FootRight],joints[KinectPV2.JointType_ShoulderLeft],joints[KinectPV2.JointType_KneeRight],5);

                //squatAngleRept(joints[KinectPV2.JointType_HipRight],joints[KinectPV2.JointType_KneeRight],joints[KinectPV2.JointType_FootRight],130,150);

/*
                estatura(joints[KinectPV2.JointType_FootLeft],joints[KinectPV2.JointType_Head]);
                dibujarEstatura();
                elevaPantorrilas(joints[KinectPV2.JointType_FootLeft],joints[KinectPV2.JointType_FootRight],joints[KinectPV2.JointType_Head],joints[KinectPV2.JointType_AnkleRight]);
*/

                vectors(joints[KinectPV2.JointType_HandRight],joints[KinectPV2.JointType_ElbowRight],joints[KinectPV2.JointType_ShoulderRight]);

                int col  = skeleton.getIndexColor();
                fill(col);
                stroke(col);

                drawBody(joints);
                drawHandState(joints[KinectPV2.JointType_HandRight]);
                drawHandState(joints[KinectPV2.JointType_HandLeft]);
            }
        }

       logica.pintar();


    }


    private void sentadilla(KJoint hipeR, KJoint kneeR) {

        float distancia=dist(hipeR.getX(),hipeR.getY(),kneeR.getX(),kneeR.getY());
        strokeWeight(2);
        stroke(255,0,0);

        fill(0,255,0);
        textSize(50);
        text(distancia,50,50);
    }


    public void unApoyo(KJoint pieL,KJoint pieR,KJoint hombroL,KJoint hombroR,int tope){

        float dpies=dist(pieL.getX(),pieL.getY(),pieR.getX(),pieR.getY());
        float dcadera=dist(pieR.getX(),pieR.getY(),hombroR.getX(),hombroR.getY());

        text("Un apoyo :"+tiempo,300,100);

        if(dcadera<dpies){
            if(frameCount%60==0){
                tiempo+=1;
            }
            textSize(20);
            text("Un apoyo :"+tiempo,300,100);
        }else {
            tiempo=0;
        }

        if(tiempo==tope){
            fill(0,255,0);
            rect(290,100,50,50);

        }
    }

    private void sentadillaDos(KJoint hipeR, KJoint hipeL, KJoint kneeR, KJoint kneeL) {
        float dkneelY = kneeL.getY() - hipeL.getY();
        float dHype = dist(hipeL.getX(), hipeL.getY(), hipeR.getX(), hipeR.getY());
//
        float distanciaHypeRodillas = dkneelY / dHype;
        fill(0,255,0);
        textSize(50);
        text(distanciaHypeRodillas,50,50);

        if (distanciaHypeRodillas < 1f) {

        } else {

        }
    }

    public void vectors(KJoint handR,KJoint elbowR,KJoint sholderR){


        PVector rightHand2D = new PVector(handR.getX(),handR.getY());
        PVector rightElbow2D = new PVector(elbowR.getX(),elbowR.getY());
        PVector rightShoulder2D = new PVector(sholderR.getX(),sholderR.getY());

        PVector upperArmOrientation = PVector.sub(rightElbow2D, rightShoulder2D);

        float elbowAngle = angleOf(rightHand2D, rightElbow2D, upperArmOrientation);

        fill(0,255,0);
       // scale(3);
        text((elbowAngle), 20, 20);
        arc(rightElbow2D.x, rightElbow2D.y, -80, -80, 0,radians(elbowAngle));



    }

    public void squatAngleSusteain(KJoint hipR, KJoint kneeR, KJoint footR, int angulo, int tope){

        PVector rightFoot2D = new PVector(hipR.getX(),hipR.getY());
        PVector rightKnee2D = new PVector(kneeR.getX(),kneeR.getY());
        PVector rightHip2D = new PVector(footR.getX(),footR.getY());

        PVector legOrientation = PVector.sub(rightKnee2D, rightHip2D);
        float elbowAngle = angleOf(rightFoot2D, rightKnee2D, legOrientation);

        fill(0,255,0);
        textSize(30);
        text((elbowAngle), 200, 20);

        if(elbowAngle<angulo+5&&elbowAngle>angulo-5){
            if(frameCount%60==0){
                tiempo+=1;
            }
            text("Sentadilla sostenida:"+tiempo,20,30);
        }else {
            tiempo=0;
        }
        if(tiempo==tope){
            fill(0,255,0);
            rect(20,30,20,20);
        }

    }

    public void dibujarEstatura(){
        stroke(0, 255, 0);
        strokeWeight(4);
        line(piex,piey,cabex,cabey);
    }

    public void estatura(KJoint footL,KJoint head){

        if(estatura) {

            piex = footL.getX();
            piey = footL.getY();
            cabex = head.getX();
            cabey = head.getY();
            estatura=false;

        }
    }

    public void elevaPantorrilas(KJoint footL,KJoint footR,KJoint ankleL, KJoint ankleR){

        PVector rightFoot2D = new PVector(footR.getX(),footR.getY());
        PVector ankleR3D = new PVector(ankleR.getX(),ankleR.getY());

        setCabex(ankleL.getX());
        setPiex(ankleL.getX());
        setCabey(ankleL.getY());

        float distancia=dist(ankleL.getX(),ankleL.getY(),cabex,cabey);

        if(ankleL.getY()<cabey-10){
            fill(0,255,0);
            rect(20,30,20,20);
        }


        stroke(0, 255, 0);
        strokeWeight(4);

        line(ankleL.getX(),ankleL.getY(),cabex,cabey);
        fill(0,255,0);
        textSize(30);
        text(distancia, 10, 20);

    }

    public void squatAngleRept(KJoint hipR, KJoint kneeR, KJoint footR, int angulo, int tope){

        PVector rightFoot2D = new PVector(hipR.getX(),hipR.getY());
        PVector rightKnee2D = new PVector(kneeR.getX(),kneeR.getY());
        PVector rightHip2D = new PVector(footR.getX(),footR.getY());

        PVector legOrientation = PVector.sub(rightKnee2D, rightHip2D);
        float elbowAngle = angleOf(rightFoot2D, rightKnee2D, legOrientation);

        fill(0,255,0);
        textSize(30);
        text((elbowAngle), 10, 20);
        text("Estado sentadillas:"+sestate,10,50);
        text("# sentadillas:"+contSentadilla,10,80);


        if(elbowAngle<angulo+5&&elbowAngle>angulo-5){
            sestate=1;
        }
        if(elbowAngle>=tope&&sestate==1){
            contSentadilla+=1;
            sestate=0;
        }
        if(tiempo==tope){
            fill(0,255,0);
            rect(20,30,20,20);
        }

    }

    float angleOf(PVector one, PVector two, PVector axis) {
        PVector limb = PVector.sub(two, one);
        return degrees(PVector.angleBetween(limb, axis));
    }


    //use different color for each skeleton tracked
    int getIndexColor(int index) {
        int col = color(255);
        if (index == 0)
            col = color(0, 0, 255);
        else  if (index == 1)
            col = color(0, 255, 0);
        else if (index == 2)
            col = color(255, 0, 0);
        else if (index == 3)
            col = color(255, 255, 0);
        else if (index == 4)
            col = color(255, 0, 255);
        else if (index == 5)
            col = color(0, 255, 255);

        return col;
    }

    //DRAW BODY
    void drawBody(KJoint[] joints) {
        drawBone(joints, KinectPV2.JointType_Head, KinectPV2.JointType_Neck);
        drawBone(joints, KinectPV2.JointType_Neck, KinectPV2.JointType_SpineShoulder);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_SpineMid);
        drawBone(joints, KinectPV2.JointType_SpineMid, KinectPV2.JointType_SpineBase);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderRight);
        drawBone(joints, KinectPV2.JointType_SpineShoulder, KinectPV2.JointType_ShoulderLeft);
        drawBone(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipRight);
        drawBone(joints, KinectPV2.JointType_SpineBase, KinectPV2.JointType_HipLeft);

        // Right Arm
        drawBone(joints, KinectPV2.JointType_ShoulderRight, KinectPV2.JointType_ElbowRight);
        drawBone(joints, KinectPV2.JointType_ElbowRight, KinectPV2.JointType_WristRight);
        drawBone(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_HandRight);
        drawBone(joints, KinectPV2.JointType_HandRight, KinectPV2.JointType_HandTipRight);
        drawBone(joints, KinectPV2.JointType_WristRight, KinectPV2.JointType_ThumbRight);

        // Left Arm
        drawBone(joints, KinectPV2.JointType_ShoulderLeft, KinectPV2.JointType_ElbowLeft);
        drawBone(joints, KinectPV2.JointType_ElbowLeft, KinectPV2.JointType_WristLeft);
        drawBone(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_HandLeft);
        drawBone(joints, KinectPV2.JointType_HandLeft, KinectPV2.JointType_HandTipLeft);
        drawBone(joints, KinectPV2.JointType_WristLeft, KinectPV2.JointType_ThumbLeft);

        // Right Leg
        drawBone(joints, KinectPV2.JointType_HipRight, KinectPV2.JointType_KneeRight);
        drawBone(joints, KinectPV2.JointType_KneeRight, KinectPV2.JointType_AnkleRight);
        drawBone(joints, KinectPV2.JointType_AnkleRight, KinectPV2.JointType_FootRight);

        // Left Leg
        drawBone(joints, KinectPV2.JointType_HipLeft, KinectPV2.JointType_KneeLeft);
        drawBone(joints, KinectPV2.JointType_KneeLeft, KinectPV2.JointType_AnkleLeft);
        drawBone(joints, KinectPV2.JointType_AnkleLeft, KinectPV2.JointType_FootLeft);

        drawJoint(joints, KinectPV2.JointType_HandTipLeft);
        drawJoint(joints, KinectPV2.JointType_HandTipRight);
        drawJoint(joints, KinectPV2.JointType_FootLeft);
        drawJoint(joints, KinectPV2.JointType_FootRight);

        drawJoint(joints, KinectPV2.JointType_ThumbLeft);
        drawJoint(joints, KinectPV2.JointType_ThumbRight);

        drawJoint(joints, KinectPV2.JointType_Head);
    }

    void drawJoint(KJoint[] joints, int jointType) {
        pushMatrix();
        translate(joints[jointType].getX(), joints[jointType].getY(), joints[jointType].getZ());
        ellipse(0, 0, 25, 25);
        popMatrix();
    }

    void drawBone(KJoint[] joints, int jointType1, int jointType2) {
        pushMatrix();
        translate(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ());
        ellipse(0, 0, 25, 25);
        popMatrix();
        line(joints[jointType1].getX(), joints[jointType1].getY(), joints[jointType1].getZ(), joints[jointType2].getX(), joints[jointType2].getY(), joints[jointType2].getZ());
    }

    void drawHandState(KJoint joint) {
        noStroke();
        handState(joint.getState());
        pushMatrix();
        translate(joint.getX(), joint.getY(), joint.getZ());
        ellipse(0, 0, 70, 70);
        popMatrix();
    }



	/*
	Different hand state
	 KinectPV2.HandState_Open
	 KinectPV2.HandState_Closed
	 KinectPV2.HandState_Lasso
	 KinectPV2.HandState_NotTracked
	 */

    void handState(int handState) {
        switch(handState) {
            case KinectPV2.HandState_Open:
                fill(0, 255, 0);
                break;
            case KinectPV2.HandState_Closed:
                fill(255, 0, 0);
                break;
            case KinectPV2.HandState_Lasso:
                fill(0, 0, 255);
                break;
            case KinectPV2.HandState_NotTracked:
                fill(100, 100, 100);
                break;
        }
    }

    public float getCabex() {
        return cabex;
    }

    public void setCabex(float cabex) {
        this.cabex = cabex;
    }

    public float getCabey() {
        return cabey;
    }

    public void setCabey(float cabey) {
        this.cabey = cabey;
    }

    public float getPiex() {
        return piex;
    }

    public void setPiex(float piex) {
        this.piex = piex;
    }
}
