
public interface CarObservable {
	void registerObservers(CarObserver Go, CarObserver To);
	void updateObservers(int state);
}
