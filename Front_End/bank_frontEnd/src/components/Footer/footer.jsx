import './style.css';
import IconBank from "../../assets/images/icon_bank.png";

// Pie de Pagina
const Footer = () => {
  return (
    <footer>
      <div className="footer-content">
        <div className="footer-logo">
          <img src={IconBank} className="footer-image" />
          <h3 className="footer-tittle">Maze Bank</h3>
        </div>
        <div className="footer-info">
          <p>© 2025 Maze Bank. Todos los derechos reservados.</p>
          <div className="social-links">
            <a
              href="https://facebook.com"
              target="_blank"
              rel="noopener noreferrer"
            >
              <img src="facebook-icon.png" alt="Facebook" />
            </a>
            <a
              href="https://twitter.com"
              target="_blank"
              rel="noopener noreferrer"
            >
              <img src="twitter-icon.png" alt="Twitter" />
            </a>
            <a
              href="https://linkedin.com"
              target="_blank"
              rel="noopener noreferrer"
            >
              <img src="linkedin-icon.png" alt="LinkedIn" />
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;