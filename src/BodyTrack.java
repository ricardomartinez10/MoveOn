/**
 * Created by ricar on 11/09/2017.
 */
/**
 * Created by ricar on 21/08/2017.
 */
import java.util.ArrayList;

import KinectPV2.KJoint;
import KinectPV2.KinectPV2;
import KinectPV2.KSkeleton;
import processing.core.*;

public class BodyTrack extends PApplet{

    KinectPV2 kinect;
    boolean estatura=true;
    private int tiempo=0;
    public KJoint[] joints;

    int sestate=0;
    int contSentadilla;

    float piex,piey,cabex,cabey=0;

    public static void main(String[] args) {
        PApplet.main(new String[] { "SkeletonDepthTest"});
    }

    public void settings(){
        size(512, 424, P3D);
    }


    public void setup() {
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
        background(0);
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

                squatAngleRept(joints[KinectPV2.JointType_HipRight],joints[KinectPV2.JointType_KneeRight],joints[KinectPV2.JointType_FootRight],130,150);

                int col  = skeleton.getIndexColor();
                fill(col);
                stroke(col);

                drawBody(joints);
                drawHandState(joints[KinectPV2.JointType_HandRight]);
                drawHandState(joints[KinectPV2.JointType_HandLeft]);
            }
        }

        // fill(255, 0, 0);
        // text(frameRate, 50, 50);


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

}
