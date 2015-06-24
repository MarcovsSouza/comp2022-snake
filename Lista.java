public class Lista {

	public Snake cobra;
	public static int size;
	public Snake inicio;

	public Lista () {
		this.cobra = null;
		size = 0;
	}

	public void inserir(Snake novo) {
		
		if (isEmpty() ) {
			cobra = novo;
			cobra.setNext(null);
		} else {
			Snake aux = cobra;
        	while(aux.getNext() != null){
            	aux = aux.getNext();
        	}
        	aux.setNext( new Snake(aux.getX(), aux.getY()));
		}
		size++;
	}

	public void remover() {
    	Snake aux = cobra;
        if( aux.getNext() == null ){
        	cobra = null;
            return;
        }
        while( aux.getNext() != null ){
            if( aux.getNext().getNext() == null ) {
                aux.setNext(null);
                break;
            }
            aux = aux.getNext();
        }
        size--;
    }
	
	public void removeAll() {
        this.cobra
        = null;
        size = 0;
    }
	
	public Snake getSnake() {
		return cobra;
	}
	
	public int getSize() {
		return this.size;
	}

	public boolean isEmpty () {
		if (cobra == null) return true;
		else return false;
	}
}
