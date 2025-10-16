package displayPanel;

public abstract class Display<T>{
    T details;
    Display(T details){
        this.details = details;
    }
    public abstract void showDetails();
}
