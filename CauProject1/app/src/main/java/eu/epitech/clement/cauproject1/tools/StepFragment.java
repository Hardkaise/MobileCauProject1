package eu.epitech.clement.cauproject1.tools;

import android.support.v4.app.Fragment;

public class StepFragment extends Fragment {
    protected StepListener stepListener;

    public static abstract class StepListener {
        public void onMessage(Object message){}
        public void onMessage(Object message, int messageCode){}
        public abstract void onNextStep();
    }

    protected void stepDone() {
        if (stepListener != null)
            stepListener.onNextStep();
    }

    protected void sendMessageToActivity(Object message) {
        if (stepListener != null)
            stepListener.onMessage(message);
    }

    protected void sendMessageToActivity(Object message, int messageCode) {
        if (stepListener != null)
            stepListener.onMessage(message, messageCode);
    }

    public void setStepListener(StepListener listener){
        this.stepListener = listener;
    }

    public boolean onBackPressed(){
        return false;
    }
}
