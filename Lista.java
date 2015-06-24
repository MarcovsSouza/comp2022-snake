public class Lista {

	public Snake cobra;
	public static int size;
	public Lista () {
		this.cobra = null;
		size = 1;
		
		/*this.cobra = new Snake();
		cobra.setProx(new Snake());
		cobra.getProx().setProx(cobra);*/
	}

	public void inserir(Snake novo) {
		/*if (isEmpty() ) {
			cobra = novo;
			cobra.setProx(null);
		} else {
			novo.setProx(cobra); 
			cobra = novo;
		}*/
		
		if(isEmpty()) {
			cobra = novo;
			novo.setProx(null);
		} else {
			Snake aux = cobra;
			while(aux.getProx() != cobra) {
				aux = aux.getProx();
			}
			aux.setProx(novo);
			novo.setProx(cobra);
		}
		size++;
	}

	public void remover() {
    	Snake aux = cobra;
        if( aux.getProx() == null ){
        	cobra = null;
            return;
        }
        while( aux.getProx() != null ){
            if( aux.getProx().getProx() == null ) {
                aux.setProx(null);
                break;
            }
            aux = aux.getProx();
        }
        size--;
    }
	
	public Snake getSnake() {
		return cobra;
	}

	public boolean isEmpty () {
		if (cobra == null) return true;
		else return false;
	}
}
