package sample.web.database.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 本サンプルでは、データアクセスはUserDetailsManagerに任せる
	@Autowired
	private UserDetailsManager userDetailsManager;

	public List<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users ORDER BY userid", new RowMapper<User>() {
			// ROLEの取得処理
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				String username = rs.getString("username");
				List<GrantedAuthority> authorities = jdbcTemplate.query(JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY,
						new String[] { username }, new RowMapper<GrantedAuthority>() {
							@Override
							public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
								String roleName = rs.getString(2);
								return new SimpleGrantedAuthority(roleName);
							}
						});

				return new User(rs.getString("username"), rs.getString("password"), authorities);
			}
		});
	}

	public void createUser(final UserDetails user) {
		userDetailsManager.createUser(user);
	}

	public void updateUser(final UserDetails user) {
		userDetailsManager.createUser(user);
	}

	public void deleteUser(String username) {
		userDetailsManager.deleteUser(username);
	}

	public boolean userExists(String username) {
		return userDetailsManager.userExists(username);
	}
}
