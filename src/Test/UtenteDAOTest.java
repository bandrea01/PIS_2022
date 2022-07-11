package Test;

public class UtenteDAOTest {
    /*@Before
    public void setUp() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        utenteDAO.add(new Utente("Valentino", "Rossi", "vr46", "123", "valentino@gmail.com", "MA"));
    }
    @After
    public void tearDown() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        utenteDAO.removeByName("vr46");
    }
    @Test
    public void findByNameTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = utenteDAO.findByName("vr46");
        Assert.assertEquals("Valentino", utente.getName());
    }
    @Test
    public void findAllTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        ArrayList<Utente> utenti = utenteDAO.findAll();
        Assert.assertEquals(11, utenti.size());
    }
    @Test
    public void removeByNameTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        int rowCount = utenteDAO.removeByName("vr46");
        Assert.assertEquals(1, rowCount);
    }
    @Test
    public void updateTest() {
        IUtenteDAO utenteDAO = UtenteDAO.getInstance();
        Utente utente = new Utente("Valentino", "Rossi", "vr46", "123", "valentino@vr46.com", "AD");
        int rowCount = utenteDAO.update(utente);
        utente = utenteDAO.findByName("vr46");
        Assert.assertEquals("valentino@vr46.com", utente.getEmail());
        Assert.assertEquals("AD", utente.getTipo());
        Assert.assertEquals(1, rowCount);
    }*/
}