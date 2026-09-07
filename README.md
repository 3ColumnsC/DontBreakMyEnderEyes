# Don't Break My Ender Eyes

---

A lightweight mod for Fabric and NeoForge that makes thrown Eyes of Ender never break. Configurable shatter chance..

**No more broken eyes**: by default, every thrown Eye of Ender drops as an item instead of shattering when it expires

**Configurable shatter chance**: keep vanilla behavior or make it harder, all with one config value

---

## ⚙️ Configuration

Config file: `config/dbmee.json`

| Option | Range | Default | Description                                                                                                    |
|---|---|---|----------------------------------------------------------------------------------------------------------------|
| `shatterChance` | 0.0 - 1.0 | 0.0 | Probability that a thrown Eye of Ender breaks instead of dropping as an item when it expires. |

Examples:
- `0.0` — never break (mod default)
- `0.2` — vanilla (20% chance to break)
- `1.0` — always break

---

## 📦 Requirements

### Fabric

- Fabric API
- Mod Menu *(optional)*
- Java 25 or newer

### NeoForge

- Java 25 or newer

---

## 📜 License

This project is licensed under the MIT License.
