package net.marcoreis.ecommerce.controlador;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import net.marcoreis.ecommerce.entidades.Categoria;
import net.marcoreis.ecommerce.negocio.CategoriaService;
import net.marcoreis.ecommerce.util.JPAUtil;

@ManagedBean
@ViewScoped
public class CategoriaBean extends BaseBean {
    private static final long serialVersionUID = 861905629535769221L;
    private Categoria categoria;
    private CategoriaService categoriaService = new CategoriaService();
    private Collection<Categoria> categorias;

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Collection<Categoria> getCategorias() {
        return categorias;
    }

    @PostConstruct
    public void init() {
        carregarCategorias();
        categoria = new Categoria();
    }

    public void salvar() {
        try {
            EntityManager em = JPAUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            categoria = em.merge(getCategoria());
            em.getTransaction().commit();
            em.close();
            infoMsg("Dados gravados com sucesso");
        } catch (Exception e) {
            errorMsg(e);
        }
    }

    public void carregarCategorias() {
        EntityManager em = JPAUtil.getInstance().getEntityManager();
        categorias = em.createQuery("from Categoria").getResultList();
        em.close();
    }

//    public String editar(Categoria categoria) {
//        return "categoria";
//    }

    public void excluir(Categoria categoria) {
        try {
            getCategoriaService().remove(categoria);
            carregarCategorias();
            infoMsg("Categoria excluída: " + categoria.getNome());
        } catch (Exception e) {
            errorMsg(e.getMessage());
        }
    }

    public CategoriaService getCategoriaService() {
        return categoriaService;
    }
}
