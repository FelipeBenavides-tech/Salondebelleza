
package model;

import java.util.List;


public interface Crud<L>{
    
    public List<L> listar();
    public int setAgregar(L tr);
    public int setActualizar(L tr);
    public int setEliminar(int tr);
    
    
   
    
    
}
